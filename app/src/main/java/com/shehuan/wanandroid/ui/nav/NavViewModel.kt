package com.shehuan.wanandroid.ui.nav

import androidx.lifecycle.MutableLiveData
import com.shehuan.wanandroid.base.BaseViewModel
import com.shehuan.wanandroid.bean.navi.NaviBean

class NavViewModel(private val repository: NavRepository) : BaseViewModel() {
    var navList = MutableLiveData<List<NaviBean>>()

    fun getNavList() {
        launch({
            navList.value = repository.getNavList()
        }, {

        })
    }
}