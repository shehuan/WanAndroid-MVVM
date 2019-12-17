package com.shehuan.wanandroid.ui.nav

import androidx.lifecycle.Observer
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.bean.navi.ArticlesItem
import com.shehuan.wanandroid.ui.website.HotWebsiteFragment
import com.shehuan.wanandroid.base.initViewModel
import com.shehuan.wanandroid.widget.VerticalTabLayout
import kotlinx.android.synthetic.main.fragment_navi.*

class NavFragment : BaseFragment() {
    private val viewModel by lazy {
        initViewModel(
            this, NavViewModel::class, NavRepository::class
        )
    }

    private val fragments: ArrayList<BaseFragment> = arrayListOf()

    companion object {
        fun newInstance() = NavFragment()
    }

    override fun initLoad() {
        statusView.showLoadingView()
        viewModel.getNavList()
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_navi
    }

    override fun initData() {
        viewModel.navList.observe(this, Observer {
            statusView.showContentView()
            val tabNames = arrayListOf<String>()
            tabNames.add(getString(R.string.hot_website))
            fragments.add(HotWebsiteFragment.newInstance())
            for (navBean in it) {
                tabNames.add(navBean.name)
                fragments.add(NavDetailFragment.newInstance(navBean.articles as ArrayList<ArticlesItem>))
            }
            naviTabLayout.addTabs(tabNames)
            initDetailFragment()
        })
    }

    override fun initView() {
        naviTabLayout.setOnTabClickListener(object : VerticalTabLayout.OnTabClickListener {
            override fun onTabClick(oldTabIndex: Int, newTabIndex: Int) {
                fragmentManager?.beginTransaction()
                    ?.hide(fragments[oldTabIndex])
                    ?.show(fragments[newTabIndex])
                    ?.commit()
            }
        })

        initStatusView(navRootLayout) {
            initLoad()
        }
    }

    private fun initDetailFragment() {
        val transition = fragmentManager!!.beginTransaction()
        for (f in fragments) {
            transition.add(R.id.naviDetailContainer, f).hide(f)
        }
        transition.show(fragments[0])
        transition.commit()
    }
}
