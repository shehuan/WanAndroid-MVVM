package com.shehuan.wanandroid.ui.home

import androidx.lifecycle.MutableLiveData
import com.shehuan.wanandroid.base.BaseViewModel
import com.shehuan.wanandroid.base.net.exception.ApiException
import com.shehuan.wanandroid.bean.BannerBean
import com.shehuan.wanandroid.bean.article.ArticleBean

class HomeViewModel(private val repository: HomeRepository) : BaseViewModel() {
    var collectSuccess = MutableLiveData<Boolean>()

    var uncollectSuccess = MutableLiveData<Boolean>()

    var bannerList = MutableLiveData<List<BannerBean>>()

    var articleList = MutableLiveData<ArticleBean>()

    var articleListFail = MutableLiveData<ApiException>()

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

    fun getBannerList() {
        launch({
            bannerList.value = repository.getBannerList()
        }, {

        })
    }

    fun getArticleList(pageNum: Int) {
        launch({
            articleList.value = repository.getArticleList(pageNum)
        }, {
            articleListFail.value = it
        })
    }
}