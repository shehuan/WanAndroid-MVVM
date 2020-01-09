package com.shehuan.wanandroid.ui.nav

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NavRepository : BaseRepository() {
    suspend fun getNavList() = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).nav().await()
    }
}