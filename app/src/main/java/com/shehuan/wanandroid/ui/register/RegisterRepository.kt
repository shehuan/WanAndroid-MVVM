package com.shehuan.wanandroid.ui.register

import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager

class RegisterRepository : BaseRepository() {
    suspend fun register(username: String, password: String, repassword: String) =
        executeRequest {
            val params = hashMapOf<String, String>()
            params["username"] = username
            params["password"] = password
            params["repassword"] = repassword
            RetrofitManager.getApis().register(params)
        }
}