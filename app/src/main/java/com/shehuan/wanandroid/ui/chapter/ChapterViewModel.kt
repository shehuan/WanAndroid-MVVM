package com.shehuan.wanandroid.ui.chapter

import androidx.lifecycle.MutableLiveData
import com.shehuan.wanandroid.base.BaseViewModel
import com.shehuan.wanandroid.base.net.exception.ApiException
import com.shehuan.wanandroid.bean.ChapterBean

class ChapterViewModel(private val repository: ChapterRepository) : BaseViewModel() {
    var chapterList = MutableLiveData<List<ChapterBean>>()
    var chapterListFail = MutableLiveData<ApiException>()

    fun getChapterList() {
        launch({
            chapterList.value = repository.getChapterList()
        }, {
            chapterListFail.value = it
        })
    }
}