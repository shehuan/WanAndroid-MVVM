package com.shehuan.wanandroid.ui.main

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.BaseRepository
import com.shehuan.wanandroid.base.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository : BaseRepository {
    suspend fun logout(){
        withContext(Dispatchers.IO){
            RetrofitManager.create(WanAndroidApis::class.java).logout().await()
        }
    }
}