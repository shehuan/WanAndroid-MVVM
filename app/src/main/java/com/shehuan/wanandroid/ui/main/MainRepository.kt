package com.shehuan.wanandroid.ui.main

import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager

class MainRepository : BaseRepository() {
    suspend fun logout() =
        RetrofitManager.getApis().logout()
}