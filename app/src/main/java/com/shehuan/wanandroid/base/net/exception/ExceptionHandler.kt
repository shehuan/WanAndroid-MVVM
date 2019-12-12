package com.shehuan.wanandroid.base.net.exception

import android.net.ParseException
import com.google.gson.JsonParseException
import com.shehuan.wanandroid.base.net.exception.Code.Companion.BAD_GATEWAY
import com.shehuan.wanandroid.base.net.exception.Code.Companion.FORBIDDEN
import com.shehuan.wanandroid.base.net.exception.Code.Companion.GATEWAY_TIMEOUT
import com.shehuan.wanandroid.base.net.exception.Code.Companion.HTTP_ERROR
import com.shehuan.wanandroid.base.net.exception.Code.Companion.INTERNAL_SERVER_ERROR
import com.shehuan.wanandroid.base.net.exception.Code.Companion.NET_ERROR
import com.shehuan.wanandroid.base.net.exception.Code.Companion.NOT_FOUND
import com.shehuan.wanandroid.base.net.exception.Code.Companion.PARSE_ERROR
import com.shehuan.wanandroid.base.net.exception.Code.Companion.REQUEST_TIMEOUT
import com.shehuan.wanandroid.base.net.exception.Code.Companion.SERVICE_UNAVAILABLE
import com.shehuan.wanandroid.base.net.exception.Code.Companion.SSL_ERROR
import com.shehuan.wanandroid.base.net.exception.Code.Companion.TIMEOUT_ERROR
import com.shehuan.wanandroid.base.net.exception.Code.Companion.UNAUTHORIZED
import com.shehuan.wanandroid.base.net.exception.Code.Companion.UNKNOWN_ERROR
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

class ExceptionHandler {
    companion object {
        fun handle(e: Throwable): ApiException {
            val responseException: ApiException
            if (e is ApiException) {
                responseException = e
            } else if (e is HttpException) {
                responseException = when (e.code()) {
                    UNAUTHORIZED, FORBIDDEN, NOT_FOUND, REQUEST_TIMEOUT, GATEWAY_TIMEOUT, INTERNAL_SERVER_ERROR, BAD_GATEWAY, SERVICE_UNAVAILABLE -> ApiException(
                        e.code(),
                        "网络连接错误（${e.code()}）"
                    )
                    else -> ApiException(HTTP_ERROR, "网络连接错误（${e.code()}）")
                }
            } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException
            ) {
                responseException = ApiException(PARSE_ERROR, "解析错误")
            } else if (e is ConnectException || e is UnknownHostException) {
                responseException = ApiException(NET_ERROR, "连接失败")
            } else if (e is ConnectTimeoutException || e is java.net.SocketTimeoutException) {
                responseException = ApiException(TIMEOUT_ERROR, "网络连接超时")
            } else if (e is javax.net.ssl.SSLHandshakeException) {
                responseException = ApiException(SSL_ERROR, "证书验证失败")
            } else {
                responseException = ApiException(UNKNOWN_ERROR, e.message ?: "未知错误")
            }
            return responseException
        }
    }
}