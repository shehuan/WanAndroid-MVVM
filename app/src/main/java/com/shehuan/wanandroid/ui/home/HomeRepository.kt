package com.shehuan.wanandroid.ui.home

import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager

class HomeRepository : BaseRepository() {
    suspend fun collectArticle(id: Int) =
        executeRequest { RetrofitManager.getApis().collectArticle(id) }

    suspend fun uncollectArticle(id: Int) =
        executeRequest { RetrofitManager.getApis().uncollectArticle(id) }

    suspend fun getBannerList() =
        executeRequest { RetrofitManager.getApis().banner() }

    suspend fun getArticleList(pageNum: Int) =
        executeRequest { RetrofitManager.getApis().articleList(pageNum) }

}