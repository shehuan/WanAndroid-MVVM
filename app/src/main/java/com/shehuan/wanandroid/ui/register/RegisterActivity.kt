package com.shehuan.wanandroid.ui.register

import android.content.Intent
import androidx.lifecycle.Observer
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.activity.BaseActivity
import com.shehuan.wanandroid.base.activity.BaseActivity2
import com.shehuan.wanandroid.databinding.ActivityRegisterBinding
import com.shehuan.wanandroid.widget.WrapTextWatcher
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity :
    BaseActivity2<ActivityRegisterBinding, RegisterViewModel, RegisterRepository>() {
    companion object {
        fun start(context: BaseActivity) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initLoad() {

    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_register
    }

    override fun initData() {
        viewModel.registerBean.observe(this, Observer {
            finish()
        })
    }

    override fun initView() {
        initToolbar(R.string.register)

        registerPasswordTTL.isPasswordVisibilityToggleEnabled = true
        registerRepasswordTTL.isPasswordVisibilityToggleEnabled = true
        registerUsernameET.addTextChangedListener(WrapTextWatcher(registerUsernameTTL))
        registerPasswordET.addTextChangedListener(WrapTextWatcher(registerPasswordTTL))
        registerRepasswordET.addTextChangedListener(WrapTextWatcher(registerRepasswordTTL))

        registerBtn.setOnClickListener {
            if (registerUsernameET.text.isEmpty()) {
                registerUsernameTTL.error = getString(R.string.username_empty)
                registerUsernameTTL.isErrorEnabled = true
                return@setOnClickListener
            }
            if (registerPasswordET.text.isEmpty()) {
                registerPasswordTTL.error = getString(R.string.password_empty)
                return@setOnClickListener
            }

            if (registerRepasswordET.text.isEmpty()) {
                registerRepasswordTTL.error = getString(R.string.repassword_empty)
                return@setOnClickListener
            }

            if (registerPasswordET.text.toString() != registerRepasswordET.text.toString()) {
                registerPasswordTTL.error = getString(R.string.password_unequal)
                registerRepasswordTTL.error = getString(R.string.password_unequal)
                return@setOnClickListener
            }

            viewModel.register(
                registerUsernameET.text.toString(),
                registerPasswordET.text.toString(),
                registerRepasswordET.text.toString()
            )
        }
    }
}
