package com.shehuan.wanandroid.base.net

data class BaseResponse<T>(val errorMsg: String, val errorCode: Int, var data: T)