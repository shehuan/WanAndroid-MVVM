package com.shehuan.wanandroid.ui.collection

import androidx.lifecycle.MutableLiveData
import com.shehuan.wanandroid.base.BaseViewModel
import com.shehuan.wanandroid.base.net.exception.ApiException
import com.shehuan.wanandroid.bean.article.ArticleBean

class MyCollectionViewModel(private val repository: MyCollectionRepository) : BaseViewModel() {
    var cancelSuccess = MutableLiveData<Boolean>()

    var collectionList = MutableLiveData<ArticleBean>()
    var collectionListFail = MutableLiveData<ApiException>()

    fun cancelCollection(id: Int, originId: Int) {
        launch({
            repository.cancelCollection(id, originId)
            cancelSuccess.value = true
        }, {
            cancelSuccess.value = false
        })
    }

    fun getCollectionList(pageNum: Int) {
        launch({
            collectionList.value = repository.getCollectionList(pageNum)
        }, {
            collectionListFail.value = it
        })
    }
}