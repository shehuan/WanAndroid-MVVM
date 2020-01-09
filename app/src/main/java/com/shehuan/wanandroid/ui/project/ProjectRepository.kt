package com.shehuan.wanandroid.ui.project

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectRepository : BaseRepository() {
    suspend fun getCategoryList() = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).projectCategory().await()
    }
}