package com.shehuan.wanandroid.ui.query

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QueryRepository : BaseRepository {
    suspend fun collectArticle(id: Int) = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).collectArticle(id).await()
    }

    suspend fun uncollectArticle(id: Int) = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).uncollectArticle(id).await()
    }

    suspend fun getHotKey() = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).hotKey().await()
    }

    suspend fun query(pageNum: Int, k: String) = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).query(pageNum, k).await()
    }
}