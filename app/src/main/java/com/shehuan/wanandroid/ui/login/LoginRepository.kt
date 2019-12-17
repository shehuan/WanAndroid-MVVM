package com.shehuan.wanandroid.ui.login

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository : BaseRepository {
    suspend fun login(username: String, password: String) = withContext(Dispatchers.IO) {
        val params = hashMapOf<String, String>()
        params["username"] = username
        params["password"] = password
        RetrofitManager.create(WanAndroidApis::class.java).login(params).await()
    }
}