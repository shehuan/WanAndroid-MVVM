package com.shehuan.wanandroid.ui.tree.treeDetail


import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.TreeDetailListAdapter
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.bean.treeDetail.DatasItem
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.ToastUtil
import com.shehuan.wanandroid.base.initViewModel
import com.shehuan.wanandroid.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.floating_button_layout.*
import kotlinx.android.synthetic.main.fragment_tree_detail.*

private const val CID = "cid"

class TreeDetailFragment : BaseFragment() {

    private val viewModel by lazy {
        initViewModel(
            this, TreeDetailViewModel::class, TreeDetailRepository::class
        )
    }

    private var pageNum: Int = 0
    private lateinit var treeDetailListAdapter: TreeDetailListAdapter
    private lateinit var collectDataItem: DatasItem
    private var collectPosition: Int = 0

    private var cid: Int = 0

    companion object {
        fun newInstance(cid: Int) =
            TreeDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt("cid", cid)
                }
            }
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_tree_detail
    }

    override fun initData() {
        arguments?.let {
            cid = it.getInt(CID)
        }

        viewModel.treeDetail.observe(this, Observer {
            if (pageNum == 0) {
                statusView.showContentView()
                treeDetailListAdapter.setNewData(it.datas)
            } else {
                treeDetailListAdapter.setLoadMoreData(it.datas)
            }
            pageNum++
            if (pageNum == it.pageCount) {
                treeDetailListAdapter.loadEnd()
            }
        })

        viewModel.treeDetailFail.observe(this, Observer {
            if (pageNum == 0) {
                statusView.showErrorView()
            } else {
                treeDetailListAdapter.loadFailed()
            }
        })

        viewModel.collectSuccess.observe(this, Observer { success ->
            hideLoading()
            if (success) {
                collectDataItem.collect = true
                treeDetailListAdapter.change(collectPosition)
                ToastUtil.show(mContext, R.string.collect_success)
            }
        })

        viewModel.uncollectSuccess.observe(this, Observer { success ->
            hideLoading()
            if (success) {
                collectDataItem.collect = false
                treeDetailListAdapter.change(collectPosition)
                ToastUtil.show(mContext, R.string.uncollect_success)
            }
        })
    }

    override fun initView() {
        // 悬浮按钮点击事件
        floatBtn.hide()
        floatBtn.setOnClickListener {
            treeDetailRv.smoothScrollToPosition(0)
        }

        treeDetailListAdapter = TreeDetailListAdapter(context, null, true).apply {
            setLoadingView(R.layout.rv_loading_layout)
            setLoadEndView(R.layout.rv_load_end_layout)
            setLoadFailedView(R.layout.rv_load_failed_layout)
            setOnItemClickListener { _, data, _ ->
                ArticleActivity.start(mContext, data.title, data.link)
            }
            setOnItemChildClickListener(R.id.treeArticleCollectIv) { _, data, position ->
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
                viewModel.getTreeDetail(pageNum, cid)

            }
        }
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        treeDetailRv.run {
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration())
            adapter = treeDetailListAdapter
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

        initStatusView(treeDetailRootLayout) {
            initLoad()
        }
    }

    override fun initLoad() {
        statusView.showLoadingView()
        viewModel.getTreeDetail(pageNum, cid)
    }
}
