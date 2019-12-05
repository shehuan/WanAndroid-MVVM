package com.shehuan.wanandroid.ui.tree.treeDetail

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TreeDetailRepository : BaseRepository {
    suspend fun getTreeDetail(pageNum: Int, cid: Int) = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).treeDetail(pageNum, cid).await()
    }

    suspend fun collectArticle(id:Int) =withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).collectArticle(id).await()
    }

    suspend fun uncollectArticle(id:Int)=withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).uncollectArticle(id).await()
    }
}