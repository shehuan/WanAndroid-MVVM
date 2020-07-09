package com.shehuan.wanandroid.ui.login

import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager

class LoginRepository : BaseRepository() {
    suspend fun login(username: String, password: String) =
        executeRequest {
            val params = hashMapOf<String, String>()
            params["username"] = username
            params["password"] = password
            RetrofitManager.getApis().login(params)
        }

}