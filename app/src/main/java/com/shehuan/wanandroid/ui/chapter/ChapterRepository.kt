package com.shehuan.wanandroid.ui.chapter

import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager

class ChapterRepository : BaseRepository() {
    suspend fun getChapterList() =
        executeRequest { RetrofitManager.getApis().chapter() }

}