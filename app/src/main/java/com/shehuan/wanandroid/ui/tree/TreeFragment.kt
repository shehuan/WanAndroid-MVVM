package com.shehuan.wanandroid.ui.tree

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.TreeListAdapter
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.ui.tree.treeDetail.TreeDetailActivity
import com.shehuan.wanandroid.base.initViewModel
import com.shehuan.wanandroid.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_tree.*

class TreeFragment : BaseFragment() {

    private val viewModel by lazy {
        initViewModel(
            this, TreeViewModel::class, TreeRepository::class
        )
    }

    private lateinit var treeListAdapter: TreeListAdapter

    companion object {
        fun newInstance() = TreeFragment()
    }

    override fun initLoad() {
        statusView.showLoadingView()
        viewModel.getTree()
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_tree
    }

    override fun initData() {
        viewModel.treeList.observe(this, Observer {
            statusView.showContentView()
            treeListAdapter.setNewData(it)
        })
        viewModel.treeFail.observe(this, Observer {
            statusView.showErrorView()
        })
    }

    override fun initView() {
        treeListAdapter = TreeListAdapter(context, null, false)
        treeListAdapter.setOnItemClickListener { _, data, _ ->
                        TreeDetailActivity.start(mContext, data.name, data.children)
        }
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        treeRv.layoutManager = linearLayoutManager
        treeRv.addItemDecoration(
            DividerItemDecoration()
                .setDividerHeight(20)
                .setDividerColor(resources.getColor(R.color.cEEEEF5))
        )
        treeRv.adapter = treeListAdapter

        initStatusView(treeRootLayout) {
            initLoad()
        }
    }
}
