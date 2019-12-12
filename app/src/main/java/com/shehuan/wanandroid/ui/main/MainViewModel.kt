package com.shehuan.wanandroid.ui.main

import com.shehuan.wanandroid.App
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.BaseViewModel
import com.shehuan.wanandroid.utils.ToastUtil
import com.shehuan.wanandroid.utils.sp.SpUtil

class MainViewModel(private val repository: MainRepository) : BaseViewModel() {
    fun logout() {
        launch({
            repository.logout()
            SpUtil.removeCookies()
            SpUtil.removeUsername()
            ToastUtil.show(App.getApp(), R.string.logout_tip)
        }, {

        })
    }
}