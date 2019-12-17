package com.shehuan.wanandroid.ui.tree.treeDetail

import androidx.lifecycle.MutableLiveData
import com.shehuan.wanandroid.base.BaseViewModel
import com.shehuan.wanandroid.base.net.exception.ApiException
import com.shehuan.wanandroid.bean.treeDetail.TreeDetailBean

class TreeDetailViewModel(private val repository: TreeDetailRepository) : BaseViewModel() {
    var treeDetail = MutableLiveData<TreeDetailBean>()
    var treeDetailFail = MutableLiveData<ApiException>()
    var collectSuccess = MutableLiveData<Boolean>()
    var uncollectSuccess = MutableLiveData<Boolean>()

    fun getTreeDetail(pageNum: Int, cid: Int) {
        launch({
            treeDetail.value = repository.getTreeDetail(pageNum, cid)
        }, {
            treeDetailFail.value = it
        })
    }

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
}