package com.shehuan.wanandroid.ui.tree

import androidx.lifecycle.MutableLiveData
import com.shehuan.wanandroid.base.BaseViewModel
import com.shehuan.wanandroid.base.net.exception.ApiException
import com.shehuan.wanandroid.bean.tree.TreeBean

class TreeViewModel(private val repository: TreeRepository) : BaseViewModel() {
    var treeList = MutableLiveData<List<TreeBean>>()
    var treeFail = MutableLiveData<ApiException>()

    fun getTree() {
        launch({
            treeList.value = repository.getTree()
        }, {
            treeFail.value = it
        })
    }
}