package com.shehuan.wanandroid.ui.website

import androidx.lifecycle.MutableLiveData
import com.shehuan.wanandroid.base.BaseViewModel
import com.shehuan.wanandroid.base.net.exception.ApiException
import com.shehuan.wanandroid.bean.FriendBean

class HotWebsiteViewModel(private val repository: HotWebsiteRepository) : BaseViewModel() {
    var friendList = MutableLiveData<List<FriendBean>>()
    var friendListFail = MutableLiveData<ApiException>()

    fun getFriendList() {
        launch({
            friendList.value = repository.getFriendList()
        }, {
            friendListFail.value = it
        })
    }
}