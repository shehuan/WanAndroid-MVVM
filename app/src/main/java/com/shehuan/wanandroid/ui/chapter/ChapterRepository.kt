package com.shehuan.wanandroid.ui.chapter

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChapterRepository : BaseRepository() {
    suspend fun getChapterList() = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).chapter().await()
    }
}