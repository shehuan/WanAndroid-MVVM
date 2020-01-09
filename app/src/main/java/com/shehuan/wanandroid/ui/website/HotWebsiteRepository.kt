package com.shehuan.wanandroid.ui.website

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HotWebsiteRepository : BaseRepository() {
    suspend fun getFriendList() = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).friend().await()
    }
}