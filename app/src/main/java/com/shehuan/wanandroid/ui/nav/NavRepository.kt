package com.shehuan.wanandroid.ui.nav

import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager

class NavRepository : BaseRepository() {
    suspend fun getNavList() =
        executeRequest { RetrofitManager.getApis().nav() }
}