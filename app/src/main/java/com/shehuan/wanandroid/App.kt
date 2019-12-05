package com.shehuan.wanandroid

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import org.litepal.LitePal

class App : MultiDexApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        LitePal.initialize(this)
    }

    companion object {
        private lateinit var instance: App
        fun getApp(): App {
            return instance
        }
    }
}