package com.shehuan.wanandroid.ui.chapter

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ChapterAdapter
import com.shehuan.wanandroid.base.fragment.BaseFragment2
import com.shehuan.wanandroid.databinding.FragmentChapterBinding
import com.shehuan.wanandroid.ui.chapter.chapterDetail.ChapterDetailActivity
import kotlinx.android.synthetic.main.fragment_chapter.*

class ChapterFragment :
    BaseFragment2<FragmentChapterBinding, ChapterViewModel, ChapterRepository>() {
    private lateinit var chapterAdapter: ChapterAdapter

    companion object {
        fun newInstance() = ChapterFragment()
    }

    override fun initLoad() {
        statusView.showLoadingView()
        viewModel.getChapterList()
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_chapter
    }

    override fun initData() {
        viewModel.chapterList.observe(this, Observer {
            statusView.showContentView()
            chapterAdapter.setNewData(it)
        })

        viewModel.chapterListFail.observe(this, Observer {
            statusView.showErrorView()
        })
    }

    override fun initView() {
        chapterAdapter = ChapterAdapter(context, null, false)
        chapterAdapter.setOnItemClickListener { _, data, _ ->
            ChapterDetailActivity.start(mContext, data.name, data.id)
        }
        val gridLayoutManager = GridLayoutManager(context, 2)
        chapterRv.layoutManager = gridLayoutManager
        chapterRv.adapter = chapterAdapter

        initStatusView(chapterRootLayout) {
            initLoad()
        }
    }
}
