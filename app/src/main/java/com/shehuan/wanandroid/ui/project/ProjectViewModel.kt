package com.shehuan.wanandroid.ui.project

import androidx.lifecycle.MutableLiveData
import com.shehuan.wanandroid.base.BaseViewModel
import com.shehuan.wanandroid.base.net.exception.ApiException
import com.shehuan.wanandroid.bean.ProjectCategoryBean

class ProjectViewModel(private val repository: ProjectRepository) : BaseViewModel() {
    var categoryList = MutableLiveData<List<ProjectCategoryBean>>()
    var categoryListFail = MutableLiveData<ApiException>()

    fun getCategoryList() {
        launch({
            categoryList.value = repository.getCategoryList()
        }, {
            categoryListFail.value = it
        })
    }
}