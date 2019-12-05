package com.shehuan.wanandroid.ui.website

import androidx.lifecycle.Observer
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.fragment.BaseFragment2
import com.shehuan.wanandroid.databinding.FragmentHotWebsiteBinding
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.addCommonView
import kotlinx.android.synthetic.main.fragment_hot_website.*


class HotWebsiteFragment :
    BaseFragment2<FragmentHotWebsiteBinding, HotWebsiteViewModel, HotWebsiteRepository>() {
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
