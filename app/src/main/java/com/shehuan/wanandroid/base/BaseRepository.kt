package com.shehuan.wanandroid.base

import com.shehuan.wanandroid.base.net.exception.ApiException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface BaseRepository {
    suspend fun <T> Call<BaseResponse<T>>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<BaseResponse<T>> {
                override fun onFailure(call: Call<BaseResponse<T>>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                @Suppress("UNCHECKED_CAST")
                override fun onResponse(call: Call<BaseResponse<T>>, response: Response<BaseResponse<T>>) {
                    val body: BaseResponse<T> = response.body() as BaseResponse<T>
                    if (0 != body.errorCode) {
                        continuation.resumeWithException(ApiException(body.errorCode, body.errorMsg))
                    }else{
                        if (body.data == null){
                            body.data ="" as T
                        }
                        continuation.resume(body.data)
                    }
                }
            })
        }
    }
}