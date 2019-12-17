package com.shehuan.wanandroid.ui.project.projectDetail

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectDetailRepository : BaseRepository {
    suspend fun collectArticle(id: Int) = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).collectArticle(id).await()
    }

    suspend fun uncollectArticle(id: Int) = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).uncollectArticle(id).await()
    }

    suspend fun getNewProjectList(pageNum: Int) = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).newProject(pageNum).await()
    }

    suspend fun getProjectList(pageNum: Int, cid: Int) = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).projectDetail(pageNum, cid).await()
    }
}