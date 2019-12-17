package com.shehuan.wanandroid.ui.login

import android.content.Intent
import android.graphics.Paint
import androidx.lifecycle.Observer
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.activity.BaseActivity
import com.shehuan.wanandroid.ui.register.RegisterActivity
import com.shehuan.wanandroid.base.initViewModel
import com.shehuan.wanandroid.widget.WrapTextWatcher
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    private val viewModel by lazy {
        initViewModel(
            this, LoginViewModel::class, LoginRepository::class
        )
    }

    companion object {
        fun start(context: BaseActivity) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initLoad() {

    }

    override fun initContentView() {
        setContentView(R.layout.activity_login)
    }

    override fun initData() {
        viewModel.loginBean.observe(this, Observer { loginBean ->
            hideLoading()
            if (loginBean != null) {
                finish()
            }
        })
    }

    override fun initView() {
        initToolbar(R.string.login)

        registerTv.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        registerTv.setOnClickListener {
            RegisterActivity.start(this)
            finish()
        }

        loginPasswordTTL.isPasswordVisibilityToggleEnabled = true
        loginUsernameET.addTextChangedListener(WrapTextWatcher(loginUsernameTTL))
        loginPasswordET.addTextChangedListener(WrapTextWatcher(loginPasswordTTL))

        loginBtn.setOnClickListener {
            if (loginUsernameET.text.isEmpty()) {
                loginUsernameTTL.error = getString(R.string.username_empty)
                loginUsernameTTL.isErrorEnabled = true
                return@setOnClickListener
            }
            if (loginPasswordET.text.isEmpty()) {
                loginPasswordTTL.error = getString(R.string.password_empty)
                return@setOnClickListener
            }
            showLoading()
            viewModel.login(loginUsernameET.text.toString(), loginPasswordET.text.toString())
        }
    }
}
