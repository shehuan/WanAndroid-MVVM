package com.shehuan.wanandroid.adapter

import android.content.Context
import com.othershe.baseadapter.ViewHolder
import com.othershe.baseadapter.base.CommonBaseAdapter
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.bean.treeDetail.DatasItem
import com.shehuan.wanandroid.databinding.RvItemTreeDetailLayoutBinding
import com.shehuan.wanandroid.base.initDataBinding

class TreeDetailListAdapter(context: Context?, data: List<DatasItem>?, isOpenLoadMore: Boolean) :
        CommonBaseAdapter<DatasItem>(context, data, isOpenLoadMore) {
    override fun getItemLayoutId(): Int {
        return R.layout.rv_item_tree_detail_layout
    }

    override fun convert(viewHolder: ViewHolder, data: DatasItem, position: Int) {
        val binding =
            initDataBinding<RvItemTreeDetailLayoutBinding>(
                viewHolder.convertView
            )
        binding.data = data
    }
}