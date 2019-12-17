package com.shehuan.wanandroid.ui.query

import androidx.lifecycle.MutableLiveData
import com.shehuan.wanandroid.base.BaseViewModel
import com.shehuan.wanandroid.base.net.exception.ApiException
import com.shehuan.wanandroid.bean.HotKeyBean
import com.shehuan.wanandroid.bean.article.ArticleBean

class QueryViewModel(private val repository: QueryRepository) : BaseViewModel() {
    var collectSuccess = MutableLiveData<Boolean>()

    var uncollectSuccess = MutableLiveData<Boolean>()

    var hotKeyList = MutableLiveData<List<HotKeyBean>>()

    var queryList = MutableLiveData<ArticleBean>()
    var queryListFail = MutableLiveData<ApiException>()

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

    fun getHotKey() {
        launch({
            hotKeyList.value = repository.getHotKey()
        }, {

        })
    }

    fun query(pageNum: Int, k: String) {
        launch({
            queryList.value = repository.query(pageNum, k)
        }, {
            queryListFail.value = it
        })
    }
}