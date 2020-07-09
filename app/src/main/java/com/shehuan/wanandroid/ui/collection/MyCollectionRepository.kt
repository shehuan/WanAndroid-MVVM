package com.shehuan.wanandroid.ui.collection

import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager

class MyCollectionRepository : BaseRepository() {
    suspend fun cancelCollection(id: Int, originId: Int) =
        executeRequest { RetrofitManager.getApis().cancelMyCollection(id, originId) }

    suspend fun getCollectionList(pageNum: Int) =
        executeRequest { RetrofitManager.getApis().collectArticleList(pageNum) }
}