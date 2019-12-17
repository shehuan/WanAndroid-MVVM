package com.shehuan.wanandroid.ui.collection

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.CollectionListAdapter
import com.shehuan.wanandroid.base.activity.BaseActivity
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.ToastUtil
import com.shehuan.wanandroid.base.initViewModel
import com.shehuan.wanandroid.widget.DividerItemDecoration
import com.shehuan.wanandroid.widget.WrapLinearLayoutManager
import kotlinx.android.synthetic.main.activity_my_collection.*

class MyCollectionActivity : BaseActivity() {

    private val viewModel by lazy {
        initViewModel(
            this, MyCollectionViewModel::class, MyCollectionRepository::class
        )
    }

    private var pageNum: Int = 0
    private lateinit var collectionListAdapter: CollectionListAdapter
    private var collectPosition: Int = 0

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MyCollectionActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initLoad() {
        statusView.showLoadingView()
        viewModel.getCollectionList(pageNum)
    }

    override fun initContentView() {
        setContentView(R.layout.activity_my_collection)
    }

    override fun initData() {
        viewModel.cancelSuccess.observe(this, Observer { success ->
            hideLoading()
            if (success) {
                collectionListAdapter.remove(collectPosition)
                ToastUtil.show(mContext, R.string.uncollect_success)
            }
        })

        viewModel.collectionList.observe(this, Observer {
            if (pageNum == 0) {
                statusView.showContentView()
                collectionListAdapter.setNewData(it.datas)
            } else {
                collectionListAdapter.setLoadMoreData(it.datas)
            }
            pageNum++
            if (pageNum == it.pageCount) {
                collectionListAdapter.loadEnd()
            }
        })

        viewModel.collectionListFail.observe(this, Observer {
            if (pageNum == 0) {
                statusView.showErrorView()
            } else {
                collectionListAdapter.loadFailed()
            }
        })
    }

    override fun initView() {
        initToolbar(R.string.collect)

        collectionListAdapter = CollectionListAdapter(mContext, null, true).apply {
            setLoadingView(R.layout.rv_loading_layout)
            setLoadEndView(R.layout.rv_load_end_layout)
            setLoadFailedView(R.layout.rv_load_failed_layout)

            setOnItemClickListener { _, data, _ ->
                ArticleActivity.start(mContext, data.title, data.link)
            }
            setOnItemChildClickListener(R.id.articleCollectIv) { _, data, position ->
                collectPosition = position
                showLoading()
                viewModel.cancelCollection(data.id, data.originId)

            }
            setOnLoadMoreListener {
                viewModel.getCollectionList(pageNum)
            }
        }
        val linearLayoutManager = WrapLinearLayoutManager(mContext)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        collectionRv.layoutManager = linearLayoutManager
        collectionRv.addItemDecoration(DividerItemDecoration())
        collectionRv.adapter = collectionListAdapter

        initStatusView(R.id.collectionRv) {
            initLoad()
        }
    }
}
