package com.shehuan.wanandroid.ui.query

import android.annotation.SuppressLint
import android.content.Intent
import androidx.core.view.MenuItemCompat
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.QueryResultAdapter
import com.shehuan.wanandroid.base.activity.BaseActivity
import com.shehuan.wanandroid.base.initViewModel
import com.shehuan.wanandroid.bean.article.DatasItem
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.*
import com.shehuan.wanandroid.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_query.*


class QueryActivity : BaseActivity() {

    private val viewModel by lazy {
        initViewModel(
            this, QueryViewModel::class, QueryRepository::class
        )
    }

    private var pageNum: Int = 0
    private lateinit var keyWord: String
    private var isInitQuery: Boolean = false
    private lateinit var queryResultAdapter: QueryResultAdapter
    private lateinit var searchView: SearchView
    // 搜索结果是否为空
    private var isEmpty: Boolean = false

    private lateinit var collectDataItem: DatasItem
    private var collectPosition: Int = 0

    companion object {
        fun start(context: BaseActivity) {
            val intent = Intent(context, QueryActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initLoad() {
        statusView.showLoadingView()
        viewModel.getHotKey()
    }

    override fun initContentView() {
        setContentView(R.layout.activity_query)
    }

    override fun initData() {
        viewModel.collectSuccess.observe(this, Observer { success ->
            hideLoading()
            if (success) {
                collectDataItem.collect = true
                queryResultAdapter.change(collectPosition)
                ToastUtil.show(mContext, R.string.collect_success)
            }
        })

        viewModel.uncollectSuccess.observe(this, Observer { success ->
            hideLoading()
            if (success) {
                collectDataItem.collect = false
                queryResultAdapter.change(collectPosition)
                ToastUtil.show(mContext, R.string.uncollect_success)
            }
        })

        viewModel.hotKeyList.observe(this, Observer {
            statusView.showContentView()
            for (hotKey in it) {
                hotKeyFL.addCommonView(
                    mContext,
                    hotKey.name,
                    R.color.c515151,
                    R.drawable.hotkey_selector
                ) {
                    flexboxClick(hotKey.name)
                }
            }
        })

        viewModel.queryList.observe(this, Observer {
            hideLoading()
            if (queryResultRv.visibility == View.GONE) {
                queryResultRv.visibility = View.VISIBLE
            }

            if (isInitQuery) {
                isInitQuery = false
                queryResultRv.scrollToPosition(0)
                queryResultAdapter.reset()
            }

            if (pageNum == 0) {
                if (it.datas.isEmpty()) {
                    isEmpty = true
                    statusView.showEmptyView()
                } else if (isEmpty) {
                    statusView.showContentView()
                }
                queryResultAdapter.setNewData(it.datas)
            } else {
                queryResultAdapter.setLoadMoreData(it.datas)
            }
            pageNum++
            if (pageNum == it.pageCount) {
                queryResultAdapter.loadEnd()
            }
        })

        viewModel.queryListFail.observe(this, Observer {
            hideLoading()
            queryResultAdapter.loadFailed()
        })
    }

    @SuppressLint("ResourceType")
    override fun initView() {
        initToolbar(R.string.query)

        // 搜索记录相关
        val queryHistoryBeans = QueryHistoryDbUtil.query()
        if (queryHistoryBeans.isNotEmpty()) {
            queryHistoryRl.visibility = View.VISIBLE
            for (queryHistory in queryHistoryBeans) {
                queryHistoryFL.addCommonView(
                    mContext,
                    queryHistory.name,
                    R.color.c8A8A8A,
                    R.drawable.query_history_selector,
                    false
                ) {
                    flexboxClick(queryHistory.name)
                }
            }
        }
        // 清空搜索记录
        clearHistoryTv.setOnClickListener {
            QueryHistoryDbUtil.clear()
            queryHistoryFL.removeAllViews()
            queryHistoryRl.visibility = View.GONE
        }

        // 搜索结果列表相关初始化
        queryResultAdapter = QueryResultAdapter(mContext, null, true).apply {
            setLoadingView(R.layout.rv_loading_layout)
            setLoadEndView(R.layout.rv_load_end_layout)
            setLoadFailedView(R.layout.rv_load_failed_layout)

            setOnItemClickListener { _, data, _ ->
                ArticleActivity.start(mContext, data.title, data.link)
            }

            setOnItemChildClickListener(R.id.articleCollectIv) { _, data, position ->
                collectDataItem = data
                collectPosition = position
                showLoading()
                if (!data.collect) {
                    viewModel.collectArticle(data.id)
                } else {
                    viewModel.uncollectArticle(data.id)
                }
            }

            setOnLoadMoreListener {
                if (keyWord.isNotEmpty()) {
                    viewModel.query(pageNum, keyWord)
                }
            }
        }

        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        queryResultRv.layoutManager = linearLayoutManager
        queryResultRv.addItemDecoration(DividerItemDecoration())
        queryResultRv.adapter = queryResultAdapter

        initStatusView(R.id.contentFl) {
            initLoad()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_query_menu_layout, menu)
        val queryItem = menu?.findItem(R.id.action_query)
        searchView = MenuItemCompat.getActionView(queryItem) as SearchView
        searchView.run {
            // 是否显示提交按钮
            isSubmitButtonEnabled = false
            // 搜索框是否展开
            isIconified = false
            queryHint = getString(R.string.query_tip1)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(content: String): Boolean {
                    clearFocus()
                    addQueryHistory(content)
                    keyWord = content
                    if (!keyWord.isEmpty()) {
                        isInitQuery = true
                        pageNum = 0
                        showLoading()
                        viewModel.query(pageNum, keyWord)
                    }
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return true
                }
            })

            // 关闭搜索
            setOnCloseListener {
                isEmpty = false
                statusView.showContentView()
                queryResultRv.visibility = View.GONE
                return@setOnCloseListener false
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * 点击搜索记录、热门搜索时调用
     */
    private fun flexboxClick(name: String) {
        addQueryHistory(name)
        if (searchView.isIconified) {
            searchView.isIconified = false
        }
        searchView.setQuery(name, true)
    }

    /**
     * 添加搜索记录
     */
    @SuppressLint("ResourceType")
    private fun addQueryHistory(name: String) {
        for (i in 0 until queryHistoryFL.childCount) {
            if (name == queryHistoryFL.childName(i)) {
                val view = queryHistoryFL.getChildAt(i)
                queryHistoryFL.removeView(view)
                queryHistoryFL.addView(view, 0)
                QueryHistoryDbUtil.update(name)
                return
            }
        }

        queryHistoryFL.addCommonView(
            mContext,
            name,
            R.color.c8A8A8A,
            R.drawable.query_history_selector
        ) {
            flexboxClick(name)
        }
        QueryHistoryDbUtil.save(name)
        queryHistoryRl.visibility = View.VISIBLE
    }
}
