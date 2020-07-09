package com.shehuan.wanandroid.ui.chapter.chapterDetail

import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager


class ChapterDetailRepository : BaseRepository() {
    suspend fun collectArticle(id: Int) =
        executeRequest { RetrofitManager.getApis().collectArticle(id) }


    suspend fun uncollectArticle(id: Int) =
        executeRequest { RetrofitManager.getApis().uncollectArticle(id) }


    suspend fun getChapterArticleList(chapterId: Int, pageNum: Int) =
        executeRequest { RetrofitManager.getApis().chapterArticleList(chapterId, pageNum) }

    suspend fun queryChapterArticle(chapterId: Int, pageNum: Int, k: String) =
        executeRequest { RetrofitManager.getApis().queryChapterArticle(chapterId, pageNum, k) }
}