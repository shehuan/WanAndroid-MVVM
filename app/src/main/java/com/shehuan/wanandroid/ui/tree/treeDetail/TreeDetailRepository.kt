package com.shehuan.wanandroid.ui.tree.treeDetail

import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager

class TreeDetailRepository : BaseRepository() {
    suspend fun getTreeDetail(pageNum: Int, cid: Int) =
        executeRequest { RetrofitManager.getApis().treeDetail(pageNum, cid) }

    suspend fun collectArticle(id: Int) =
        executeRequest { RetrofitManager.getApis().collectArticle(id) }

    suspend fun uncollectArticle(id: Int) =
        executeRequest { RetrofitManager.getApis().uncollectArticle(id) }
}