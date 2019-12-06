package com.shehuan.wanandroid.adapter

import android.content.Context
import androidx.databinding.DataBindingUtil
import com.othershe.baseadapter.ViewHolder
import com.othershe.baseadapter.base.CommonBaseAdapter
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.bean.chapter.DatasItem
import com.shehuan.wanandroid.databinding.RvItemChapterDetailLayoutBinding

class ChapterDetailListAdapter(context: Context?, data: List<DatasItem>?, isOpenLoadMore: Boolean) :
    CommonBaseAdapter<DatasItem>(context, data, isOpenLoadMore) {
    override fun getItemLayoutId(): Int {
        return R.layout.rv_item_chapter_detail_layout
    }

    override fun convert(viewHolder: ViewHolder, data: DatasItem, position: Int) {
        val binding = DataBindingUtil.bind<RvItemChapterDetailLayoutBinding>(viewHolder.convertView)
        binding?.data = data
    }
}