package com.shehuan.wanandroid.ui.register

import androidx.lifecycle.MutableLiveData
import com.shehuan.wanandroid.base.BaseViewModel
import com.shehuan.wanandroid.bean.RegisterBean
import com.shehuan.wanandroid.bean.event.AccountEvent
import com.shehuan.wanandroid.utils.sp.SpUtil
import org.greenrobot.eventbus.EventBus

class RegisterViewModel(private val repository: RegisterRepository) : BaseViewModel() {
    var registerBean = MutableLiveData<RegisterBean>()

    fun register(username: String, password: String, repassword: String) {
        launch({
            registerBean.value = repository.register(username, password, repassword)
            SpUtil.setUsername(registerBean.value!!.username)
            EventBus.getDefault().post(AccountEvent())
        }, {
            registerBean.value = null
        })
    }
}