package com.shehuan.wanandroid.ui.website

import androidx.lifecycle.Observer
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.addCommonView
import com.shehuan.wanandroid.base.initViewModel
import kotlinx.android.synthetic.main.fragment_hot_website.*


class HotWebsiteFragment : BaseFragment() {

    private val viewModel by lazy {
        initViewModel(
            this, HotWebsiteViewModel::class, HotWebsiteRepository::class
        )
    }

    companion object {
        fun newInstance() = HotWebsiteFragment()
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_hot_website
    }

    override fun initData() {
        viewModel.friendList.observe(this, Observer {
            statusView.showContentView()
            for (website in it) {
                websiteFL.addCommonView(
                    mContext,
                    website.name,
                    R.color.c2C2C2C,
                    R.drawable.website_selecter
                ) {
                    ArticleActivity.start(mContext, website.name, website.link)
                }
            }
        })

        viewModel.friendListFail.observe(this, Observer {
            statusView.showErrorView()
        })
    }

    override fun initView() {
        initStatusView(hotWebsiteHomeLayout) {
            initLoad()
        }
    }

    override fun initLoad() {
        statusView.showLoadingView()
        viewModel.getFriendList()
    }
}
