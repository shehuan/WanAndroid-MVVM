package com.shehuan.wanandroid.adapter

import android.content.Context
import com.othershe.baseadapter.ViewHolder
import com.othershe.baseadapter.base.CommonBaseAdapter
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.bean.article.DatasItem
import com.shehuan.wanandroid.databinding.RvItemArticleLayoutBinding
import com.shehuan.wanandroid.base.initDataBinding

class QueryResultAdapter(context: Context?, data: List<DatasItem>?, isOpenLoadMore: Boolean) :
    CommonBaseAdapter<DatasItem>(context, data, isOpenLoadMore) {
    override fun getItemLayoutId(): Int {
        return R.layout.rv_item_article_layout
    }

    override fun convert(viewHolder: ViewHolder, data: DatasItem, position: Int) {
        val binding =
            initDataBinding<RvItemArticleLayoutBinding>(
                viewHolder.convertView
            )
        binding.data = data
    }
}