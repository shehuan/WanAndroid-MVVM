package com.shehuan.wanandroid.ui.home

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ArticleListAdapter
import com.shehuan.wanandroid.bean.BannerBean
import com.shehuan.wanandroid.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.bean.article.DatasItem
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.ToastUtil
import com.youth.banner.Banner
import com.shehuan.wanandroid.utils.BannerImageLoader
import com.shehuan.wanandroid.base.initViewModel
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.floating_button_layout.*


class HomeFragment : BaseFragment() {

    private val viewModel by lazy {
        initViewModel(
            this, HomeViewModel::class, HomeRepository::class
        )
    }

    private var pageNum: Int = 0
    private lateinit var articleListAdapter: ArticleListAdapter
    private lateinit var collectDataItem: DatasItem
    private var collectPosition: Int = 0

    private lateinit var bannerBeans: List<BannerBean>
    private lateinit var banner: Banner

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun initLoad() {
        statusView.showLoadingView()
        viewModel.getArticleList(pageNum)
        viewModel.getBannerList()
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
        viewModel.collectSuccess.observe(this, Observer { success ->
            hideLoading()
            if (success) {
                collectDataItem.collect = true
                articleListAdapter.change(collectPosition + 1)
                ToastUtil.show(mContext, R.string.collect_success)
            }
        })

        viewModel.uncollectSuccess.observe(this, Observer { success ->
            hideLoading()
            if (success) {
                collectDataItem.collect = false
                articleListAdapter.change(collectPosition + 1)
                ToastUtil.show(mContext, R.string.uncollect_success)
            }
        })

        viewModel.bannerList.observe(this, Observer {
            statusView.showContentView()
            bannerBeans = it
            val images = arrayListOf<String>()
            val titles = arrayListOf<String>()

            for (bannerBean in it) {
                images.add(bannerBean.imagePath)
                titles.add(bannerBean.title)
            }
            banner.run {
                setImages(images)
                setBannerTitles(titles)
                start()
            }
        })

        viewModel.articleList.observe(this, Observer {
            if (pageNum == 0) {
                articleListAdapter.setNewData(it.datas)
            } else {
                articleListAdapter.setLoadMoreData(it.datas)
            }
            pageNum++
            if (pageNum == it.pageCount) {
                articleListAdapter.loadEnd()
            }
        })

        viewModel.articleListFail.observe(this, Observer {
            articleListAdapter.loadFailed()
        })
    }

    override fun initView() {
        // 初始化banner
        banner = LayoutInflater.from(context).inflate(
            R.layout.home_banner_layout,
            homeRootLayout,
            false
        ) as Banner
        banner.run {
            setImageLoader(BannerImageLoader())
            setDelayTime(3000)
            setIndicatorGravity(BannerConfig.RIGHT)
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
            setOnBannerListener {
                ArticleActivity.start(mContext, bannerBeans[it].title, bannerBeans[it].url)
            }
        }

        // 悬浮按钮点击事件
        floatBtn.hide()
        floatBtn.setOnClickListener {
            articleRv.smoothScrollToPosition(0)
        }

        articleListAdapter = ArticleListAdapter(context, null, true).apply {
            setLoadingView(R.layout.rv_loading_layout)
            setLoadEndView(R.layout.rv_load_end_layout)
            setLoadFailedView(R.layout.rv_load_failed_layout)
            // 添加banner
            addHeaderView(banner)

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
                viewModel.getArticleList(pageNum)
            }
        }

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        articleRv.run {
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration())
            adapter = articleListAdapter
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

        initStatusView(homeRootLayout) {
            initLoad()
        }
    }
}
