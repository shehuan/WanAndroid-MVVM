package com.shehuan.wanandroid.ui.project

import android.text.Html
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ViewPagerAdapter
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.ui.project.projectDetail.ProjectDetailFragment
import com.shehuan.wanandroid.base.initViewModel
import kotlinx.android.synthetic.main.fragment_project.*

class ProjectFragment : BaseFragment() {

    private val viewModel by lazy {
        initViewModel(
            this, ProjectViewModel::class, ProjectRepository::class
        )
    }

    companion object {
        fun newInstance() = ProjectFragment()
    }

    override fun initLoad() {
        statusView.showLoadingView()
        viewModel.getCategoryList()
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_project
    }

    override fun initData() {
        viewModel.categoryList.observe(this, Observer {
            statusView.showContentView()
            projectTabLayout.visibility = View.VISIBLE
            val titles = arrayListOf<String>()
            val fragments = arrayListOf<BaseFragment>()
            titles.add(getString(R.string.new_project))
            fragments.add(ProjectDetailFragment.newInstance(-1))
            for (category in it) {
                titles.add(Html.fromHtml(category.name).toString())
                fragments.add(ProjectDetailFragment.newInstance(category.id))
            }

            val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
            viewPagerAdapter.setFragmentsAndTitles(fragments, titles)
            projectViewPager.offscreenPageLimit = it.size + 1
            projectViewPager.adapter = viewPagerAdapter
            projectTabLayout.tabMode = TabLayout.MODE_SCROLLABLE
            projectTabLayout.setupWithViewPager(projectViewPager)
        })

        viewModel.categoryListFail.observe(this, Observer {
            statusView.showErrorView()
        })
    }

    override fun initView() {
        initStatusView(R.id.projectViewPager) {
            initLoad()
        }
    }
}
