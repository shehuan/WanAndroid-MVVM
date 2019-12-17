package com.shehuan.wanandroid.ui.register

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterRepository : BaseRepository {
    suspend fun register(username: String, password: String, repassword: String) = withContext(Dispatchers.IO) {
        val params = hashMapOf<String, String>()
        params["username"] = username
        params["password"] = password
        params["repassword"] = repassword
        RetrofitManager.create(WanAndroidApis::class.java).register(params).await()
    }
}