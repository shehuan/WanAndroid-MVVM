package com.shehuan.wanandroid.ui.project.projectDetail

import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager

class ProjectDetailRepository : BaseRepository() {
    suspend fun collectArticle(id: Int) =
        executeRequest { RetrofitManager.getApis().collectArticle(id) }

    suspend fun uncollectArticle(id: Int) =
        executeRequest { RetrofitManager.getApis().uncollectArticle(id) }

    suspend fun getNewProjectList(pageNum: Int) =
        executeRequest { RetrofitManager.getApis().newProject(pageNum) }

    suspend fun getProjectList(pageNum: Int, cid: Int) =
        executeRequest { RetrofitManager.getApis().projectDetail(pageNum, cid) }

}