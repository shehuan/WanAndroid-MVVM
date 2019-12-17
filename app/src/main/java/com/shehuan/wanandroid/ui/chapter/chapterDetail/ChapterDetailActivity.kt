package com.shehuan.wanandroid.ui.chapter.chapterDetail

import android.content.Intent
import androidx.core.view.MenuItemCompat
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ChapterDetailListAdapter
import com.shehuan.wanandroid.base.activity.BaseActivity
import com.shehuan.wanandroid.bean.chapter.DatasItem
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.ToastUtil
import com.shehuan.wanandroid.base.initViewModel
import com.shehuan.wanandroid.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_chapter_detail.*
import kotlinx.android.synthetic.main.floating_button_layout.*

class ChapterDetailActivity : BaseActivity() {

    private val viewModel by lazy {
        initViewModel(
            this, ChapterDetailViewModel::class, ChapterDetailRepository::class
        )
    }

    private var pageNum: Int = 0
    private var chapterId: Int = 0
    private lateinit var title: String

    private lateinit var chapterDetailListAdapter: ChapterDetailListAdapter

    private var queryPageNum: Int = 0
    private lateinit var keyWord: String
    private lateinit var queryResultAdapter: ChapterDetailListAdapter
    private var isInitQuery: Boolean = false
    // 搜索结果是否为空
    private var isEmpty: Boolean = false

    private lateinit var collectDataItem: DatasItem
    private var collectPosition: Int = 0

    companion object {
        fun start(context: BaseActivity, title: String, chapterId: Int) {
            val intent = Intent(context, ChapterDetailActivity::class.java)
            intent.apply {
                putExtra("chapterId", chapterId)
                putExtra("title", title)
            }
            context.startActivity(intent)
        }
    }

    override fun initLoad() {
        statusView.showLoadingView()
        viewModel.getChapterArticleList(chapterId, pageNum)
    }

    override fun initContentView() {
        setContentView(R.layout.activity_chapter_detail)
    }

    override fun initData() {
        intent?.let {
            title = it.getStringExtra("title")
            chapterId = it.getIntExtra("chapterId", 0)
        }

        viewModel.collectSuccess.observe(this, Observer { success ->
            hideLoading()
            if (success) {
                collectDataItem.collect = true
                chapterDetailListAdapter.change(collectPosition)
                ToastUtil.show(mContext, R.string.collect_success)
            }
        })

        viewModel.uncollectSuccess.observe(this, Observer { success ->
            hideLoading()
            if (success) {
                collectDataItem.collect = false
                chapterDetailListAdapter.change(collectPosition)
                ToastUtil.show(mContext, R.string.uncollect_success)
            }
        })

        viewModel.articleBean.observe(this, Observer {
            if (pageNum == 0) {
                statusView.showContentView()
                chapterDetailListAdapter.setNewData(it.datas)
            } else {
                chapterDetailListAdapter.setLoadMoreData(it.datas)
            }
            pageNum++
            if (pageNum == it.pageCount) {
                chapterDetailListAdapter.loadEnd()
            }
        })

        viewModel.articleBeanFail.observe(this, Observer {
            if (pageNum == 0) {
                statusView.showErrorView()
            } else {
                chapterDetailListAdapter.loadFailed()
            }
        })

        viewModel.queryArticleBean.observe(this, Observer {
            hideLoading()
            if (queryChapterRv.visibility == View.GONE) {
                queryChapterRv.visibility = View.VISIBLE
            }

            if (isInitQuery) {
                isInitQuery = false
                queryChapterRv.scrollToPosition(0)
                queryResultAdapter.reset()
            }

            if (queryPageNum == 0) {
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
            queryPageNum++
            if (queryPageNum == it.pageCount) {
                queryResultAdapter.loadEnd()
            }
        })

        viewModel.queryArticleBeanFail.observe(this, Observer {
            hideLoading()
            queryResultAdapter.loadEnd()
        })
    }

    override fun initView() {
        initToolbar(title)
        initChapterList()
        initQueryChapterList()

        initStatusView(R.id.contentFl) {
            initLoad()
        }
    }

    /**
     * 文章列表初始化
     */
    private fun initChapterList() {
        // 悬浮按钮点击事件
        floatBtn.hide()
        floatBtn.setOnClickListener {
            chapterDetailRv.smoothScrollToPosition(0)
        }

        chapterDetailListAdapter = ChapterDetailListAdapter(mContext, null, true).apply {
            setLoadingView(R.layout.rv_loading_layout)
            setLoadEndView(R.layout.rv_load_end_layout)
            setLoadFailedView(R.layout.rv_load_failed_layout)

            setOnItemClickListener { _, data, _ ->
                ArticleActivity.start(mContext, data.title, data.link)
            }
            setOnItemChildClickListener(R.id.chapterArticleCollectIv) { _, data, position ->
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
                viewModel.getChapterArticleList(chapterId, pageNum)
            }
        }
        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        chapterDetailRv.run {
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration())
            adapter = chapterDetailListAdapter
            // 控制悬浮按钮
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (linearLayoutManager.findLastVisibleItemPosition() > 10) {
                        floatBtn.show()
                    } else {
                        floatBtn.hide()
                    }
                }
            })
        }
    }

    /**
     * 搜索列表初始化
     */
    private fun initQueryChapterList() {
        queryResultAdapter = ChapterDetailListAdapter(mContext, null, true).apply {
            setLoadingView(R.layout.rv_loading_layout)
            setLoadEndView(R.layout.rv_load_end_layout)
            setLoadFailedView(R.layout.rv_load_failed_layout)

            setOnItemClickListener { _, data, _ ->
                ArticleActivity.start(mContext, data.title, data.link)
            }
            setOnItemChildClickListener(R.id.chapterArticleCollectIv) { _, data, position ->
                collectDataItem = data
                collectPosition = position
                if (!data.collect) {
                    viewModel.collectArticle(data.id)
                } else {
                    viewModel.uncollectArticle(data.id)
                }
            }
            setOnLoadMoreListener {
                viewModel.queryChapterArticle(chapterId, pageNum, keyWord)
            }
        }
        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        queryChapterRv.layoutManager = linearLayoutManager
        queryChapterRv.addItemDecoration(DividerItemDecoration())
        queryChapterRv.adapter = queryResultAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_query_menu_layout, menu)
        val queryItem = menu?.findItem(R.id.action_query)
        val searchView = MenuItemCompat.getActionView(queryItem) as SearchView
        searchView.run {
            // 是否显示提交按钮
            isSubmitButtonEnabled = false
            // 搜索框是否展开,false表示展开
            isIconified = true
            queryHint = getString(R.string.query_tip)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(content: String): Boolean {
                    searchView.clearFocus()
                    keyWord = content
                    if (!keyWord.isEmpty()) {
                        queryPageNum = 0
                        isInitQuery = true
                        showLoading()
                        viewModel.queryChapterArticle(chapterId, queryPageNum, keyWord)
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
                queryChapterRv.visibility = View.GONE
                return@setOnCloseListener false
            }
        }

        return super.onCreateOptionsMenu(menu)
    }
}
