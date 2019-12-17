package com.shehuan.wanandroid.ui.project.projectDetail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ProjectListAdapter
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.bean.project.DatasItem
import com.shehuan.wanandroid.bean.project.ProjectBean
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.ToastUtil
import com.shehuan.wanandroid.base.initViewModel
import com.shehuan.wanandroid.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.floating_button_layout.*
import kotlinx.android.synthetic.main.fragment_project_detail.*

private const val CID = "cid"

class ProjectDetailFragment : BaseFragment() {

    private val viewModel by lazy {
        initViewModel(
            this, ProjectDetailViewModel::class, ProjectDetailRepository::class
        )
    }

    private var pageNum: Int = 0
    private lateinit var projectListAdapter: ProjectListAdapter
    private lateinit var collectDataItem: DatasItem
    private var collectPosition: Int = 0

    private var cid: Int = 0

    companion object {
        fun newInstance(cid: Int) =
            ProjectDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt("cid", cid)
                }
            }
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_project_detail
    }

    override fun initData() {
        arguments?.let {
            cid = it.getInt(CID)
        }

        viewModel.collectSuccess.observe(this, Observer { success ->
            hideLoading()
            if (success) {
                collectDataItem.collect = true
                projectListAdapter.change(collectPosition)
                ToastUtil.show(mContext, R.string.collect_success)
            }
        })

        viewModel.uncollectSuccess.observe(this, Observer { success ->
            hideLoading()
            if (success) {
                collectDataItem.collect = false
                projectListAdapter.change(collectPosition)
                ToastUtil.show(mContext, R.string.uncollect_success)
            }
        })

        viewModel.newProjectList.observe(this, Observer {
            setData(it)
        })

        viewModel.newProjectListFail.observe(this, Observer {
            if (pageNum == 0) {
                statusView.showErrorView()
            } else {
                projectListAdapter.loadFailed()
            }
        })

        viewModel.projectList.observe(this, Observer {
            setData(it)
        })

        viewModel.projectListFail.observe(this, Observer {
            if (pageNum == 0) {
                statusView.showErrorView()
            } else {
                projectListAdapter.loadFailed()
            }
        })
    }

    override fun initView() {
        // 悬浮按钮点击事件
        floatBtn.hide()
        floatBtn.setOnClickListener {
            projectRv.smoothScrollToPosition(0)
        }

        projectListAdapter = ProjectListAdapter(context, null, true).apply {
            setLoadingView(R.layout.rv_loading_layout)
            setLoadEndView(R.layout.rv_load_end_layout)
            setLoadFailedView(R.layout.rv_load_failed_layout)
            setOnItemClickListener { _, data, _ ->
                ArticleActivity.start(mContext, data.title, data.link)
            }
            setOnItemChildClickListener(R.id.projectCollectIv) { _, data, position ->
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
                if (cid == -1) {
                    viewModel.getNewProjectList(pageNum)
                } else {
                    viewModel.getProjectList(pageNum, cid)
                }
            }
        }
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        projectRv.run {
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration())
            adapter = projectListAdapter
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

        initStatusView(projectDetailRootLayout) {
            initLoad()
        }
    }

    override fun initLoad() {
        statusView.showLoadingView()
        if (cid == -1) {
            viewModel.getNewProjectList(pageNum)
        } else {
            viewModel.getProjectList(pageNum, cid)
        }
    }

    private fun setData(data: ProjectBean) {
        if (pageNum == 0) {
            statusView.showContentView()
            projectListAdapter.setNewData(data.datas)
        } else {
            projectListAdapter.setLoadMoreData(data.datas)
        }
        pageNum++
        if (pageNum == data.pageCount) {
            projectListAdapter.loadEnd()
            return
        }
    }
}
