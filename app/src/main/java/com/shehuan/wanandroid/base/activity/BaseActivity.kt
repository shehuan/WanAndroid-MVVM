package com.shehuan.wanandroid.base.activity

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.shehuan.statusview.StatusView
import com.shehuan.statusview.StatusViewBuilder
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.widget.LoadingDialog
import kotlinx.android.synthetic.main.toolbar_layout.*

abstract class BaseActivity : AppCompatActivity() {
    protected val TAG: String = this.javaClass.simpleName

    lateinit var mContext: BaseActivity

    abstract fun initContentView()

    abstract fun initData()

    abstract fun initView()

    abstract fun initLoad()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
        mContext = this

        // 实现APP黑白化
//        val paint = Paint()
//        val cm = ColorMatrix();
//        cm.setSaturation(0f)
//        paint.colorFilter = ColorMatrixColorFilter(cm)
//        window.decorView.setLayerType(View.LAYER_TYPE_HARDWARE, paint)

        initData()
        initView()
        initLoad()
    }

    protected lateinit var statusView: StatusView

    protected fun initStatusView(id: Int, errorRetry: (View) -> Unit) {
        statusView = StatusView.init(this, id).apply {
            setLoadingView(R.layout.dialog_loading_layout)
            config(
                StatusViewBuilder.Builder()
                    .showEmptyRetry(false)
                    .setOnErrorRetryClickListener(errorRetry)
                    .build()
            )
        }
    }

    protected fun initToolbar(@StringRes titleId: Int) {
        initToolbar(getString(titleId))
    }

    protected fun initToolbar(titleStr: String) {
        toolbar.run {
            title = titleStr
            setSupportActionBar(this)
            setNavigationOnClickListener {
                finish()
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private var loadingDialog: LoadingDialog? = null

    protected fun showLoading() {
        if (loadingDialog == null || loadingDialog?.dialog == null) {
            loadingDialog = LoadingDialog.newInstance()
            loadingDialog!!.show(supportFragmentManager)
        }
    }

    protected fun hideLoading() {
        if (loadingDialog != null) {
            loadingDialog!!.dismiss()
        }
    }
}