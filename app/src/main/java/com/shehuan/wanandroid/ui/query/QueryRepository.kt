package com.shehuan.wanandroid.ui.query

import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager

class QueryRepository : BaseRepository() {
    suspend fun collectArticle(id: Int) =
        executeRequest { RetrofitManager.getApis().collectArticle(id) }

    suspend fun uncollectArticle(id: Int) =
        executeRequest { RetrofitManager.getApis().uncollectArticle(id) }

    suspend fun getHotKey() =
        executeRequest { RetrofitManager.getApis().hotKey() }

    suspend fun query(pageNum: Int, k: String) =
        executeRequest { RetrofitManager.getApis().query(pageNum, k) }

}