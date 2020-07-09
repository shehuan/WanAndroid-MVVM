package com.shehuan.wanandroid.ui.website

import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager

class HotWebsiteRepository : BaseRepository() {
    suspend fun getFriendList() =
        executeRequest { RetrofitManager.getApis().friend() }
}