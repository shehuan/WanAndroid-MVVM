package com.shehuan.wanandroid.ui.home

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository : BaseRepository() {
    suspend fun collectArticle(id: Int) = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).collectArticle(id).await()
    }

    suspend fun uncollectArticle(id: Int) = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).uncollectArticle(id).await()
    }

    suspend fun getBannerList() = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).banner().await()
    }

    suspend fun getArticleList(pageNum: Int) = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).articleList(pageNum).await()
    }
}