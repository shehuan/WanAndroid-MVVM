package com.shehuan.wanandroid.ui.chapter.chapterDetail

import androidx.lifecycle.MutableLiveData
import com.shehuan.wanandroid.base.BaseViewModel
import com.shehuan.wanandroid.base.net.exception.ApiException
import com.shehuan.wanandroid.bean.chapter.ChapterArticleBean

class ChapterDetailViewModel(private val repository: ChapterDetailRepository) : BaseViewModel() {
    var collectSuccess = MutableLiveData<Boolean>()

    var uncollectSuccess = MutableLiveData<Boolean>()

    var articleBean = MutableLiveData<ChapterArticleBean>()
    var articleBeanFail = MutableLiveData<ApiException>()

    var queryArticleBean = MutableLiveData<ChapterArticleBean>()
    var queryArticleBeanFail = MutableLiveData<ApiException>()


    fun collectArticle(id: Int) {
        launch({
            repository.collectArticle(id)
            collectSuccess.value = true
        }, {
            collectSuccess.value = false
        })
    }

    fun uncollectArticle(id: Int) {
        launch({
            repository.uncollectArticle(id)
            uncollectSuccess.value = true
        }, {
            uncollectSuccess.value = false
        })
    }

    fun getChapterArticleList(chapterId: Int, pageNum: Int) {
        launch({
            articleBean.value = repository.getChapterArticleList(chapterId, pageNum)
        }, {
            articleBeanFail.value = it
        })
    }

    fun queryChapterArticle(chapterId: Int, pageNum: Int, k: String) {
        launch({
            queryArticleBean.value = repository.queryChapterArticle(chapterId, pageNum, k)
        }, {
            queryArticleBeanFail.value = it
        })
    }
}