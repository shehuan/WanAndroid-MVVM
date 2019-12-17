package com.shehuan.wanandroid.ui.tree

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TreeRepository : BaseRepository {
    suspend fun getTree() = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).tree().await()
    }
}