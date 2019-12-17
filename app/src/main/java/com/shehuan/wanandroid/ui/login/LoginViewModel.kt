package com.shehuan.wanandroid.ui.login

import androidx.lifecycle.MutableLiveData
import com.shehuan.wanandroid.base.BaseViewModel
import com.shehuan.wanandroid.bean.LoginBean
import com.shehuan.wanandroid.bean.event.AccountEvent
import com.shehuan.wanandroid.utils.sp.SpUtil
import org.greenrobot.eventbus.EventBus

class LoginViewModel(private val repository: LoginRepository) : BaseViewModel() {
    var loginBean = MutableLiveData<LoginBean>()

    fun login(username: String, password: String) {
        launch({
            loginBean.value = repository.login(username, password)
            SpUtil.setUsername(loginBean.value!!.username)
            EventBus.getDefault().post(AccountEvent())
        }, {
            loginBean.value = null
        })
    }
}