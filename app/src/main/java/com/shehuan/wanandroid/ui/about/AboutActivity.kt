package com.shehuan.wanandroid.ui.about

import android.content.Intent
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.activity.BaseActivity

class AboutActivity : BaseActivity() {
    companion object {
        fun start(context: BaseActivity) {
            val intent = Intent(context, AboutActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initContentView() {
        setContentView(R.layout.activity_about)
    }

    override fun initData() {

    }

    override fun initView() {
        initToolbar(R.string.about)
    }

    override fun initLoad() {

    }
}
