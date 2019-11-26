package com.shehuan.wanandroid.base.net.exception

class ApiException(val errorCode: Int, val errorMessage: String) : RuntimeException(errorMessage)