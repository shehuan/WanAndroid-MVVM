package com.shehuan.wanandroid.ui.project.projectDetail

import androidx.lifecycle.MutableLiveData
import com.shehuan.wanandroid.base.BaseViewModel
import com.shehuan.wanandroid.base.net.exception.ApiException
import com.shehuan.wanandroid.bean.ProjectCategoryBean
import com.shehuan.wanandroid.bean.project.ProjectBean

class ProjectDetailViewModel(private val repository: ProjectDetailRepository) : BaseViewModel() {
    var collectSuccess = MutableLiveData<Boolean>()

    var uncollectSuccess = MutableLiveData<Boolean>()

    var newProjectList = MutableLiveData<ProjectBean>()
    var newProjectListFail = MutableLiveData<ApiException>()

    var projectList = MutableLiveData<ProjectBean>()
    var projectListFail = MutableLiveData<ApiException>()

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

    fun getNewProjectList(pageNum: Int) {
        launch({
            newProjectList.value = repository.getNewProjectList(pageNum)
        }, {
            newProjectListFail.value = it
        })
    }

    fun getProjectList(pageNum: Int, cid: Int) {
        launch({
            projectList.value = repository.getProjectList(pageNum, cid)
        }, {
            projectListFail.value = it
        })
    }
}