package com.shehuan.wanandroid.adapter

import android.content.Context
import com.othershe.baseadapter.ViewHolder
import com.othershe.baseadapter.base.CommonBaseAdapter
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.bean.tree.TreeBean
import com.shehuan.wanandroid.databinding.RvItemTreeLayoutBinding
import com.shehuan.wanandroid.base.initDataBinding

class TreeListAdapter(context: Context?, data: List<TreeBean>?, isOpenLoadMore: Boolean) :
        CommonBaseAdapter<TreeBean>(context, data, isOpenLoadMore) {
    override fun getItemLayoutId(): Int {
        return R.layout.rv_item_tree_layout
    }

    override fun convert(viewHolder: ViewHolder, data: TreeBean, position: Int) {
        val binding =
            initDataBinding<RvItemTreeLayoutBinding>(
                viewHolder.convertView
            )
        binding.data = data
    }
}