package com.shehuan.wanandroid.ui.chapter.chapterDetail

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChapterDetailRepository : BaseRepository {
    suspend fun collectArticle(id: Int) = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).collectArticle(id).await()
    }

    suspend fun uncollectArticle(id: Int) = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).uncollectArticle(id).await()
    }

    suspend fun getChapterArticleList(chapterId: Int, pageNum: Int) = withContext(Dispatchers.IO) {
        RetrofitManager.create(WanAndroidApis::class.java).chapterArticleList(chapterId, pageNum).await()
    }

    suspend fun queryChapterArticle(chapterId: Int, pageNum: Int, k: String) =
        withContext(Dispatchers.IO) {
            RetrofitManager.create(WanAndroidApis::class.java)
                .queryChapterArticle(chapterId, pageNum, k).await()
        }
}