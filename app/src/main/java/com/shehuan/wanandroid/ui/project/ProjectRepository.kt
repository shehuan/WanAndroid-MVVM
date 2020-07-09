package com.shehuan.wanandroid.ui.project

import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager

class ProjectRepository : BaseRepository() {
    suspend fun getCategoryList() =
        executeRequest { RetrofitManager.getApis().projectCategory() }
}