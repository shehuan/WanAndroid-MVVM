package com.shehuan.wanandroid.base.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.shehuan.statusview.StatusView
import com.shehuan.statusview.StatusViewBuilder
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.BaseRepository
import com.shehuan.wanandroid.base.BaseViewModel
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.lang.reflect.ParameterizedType

abstract class BaseActivity2<VDB : ViewDataBinding, BVM : BaseViewModel, BR : BaseRepository> :
    BaseActivity() {

    protected lateinit var viewModel: BVM

    protected lateinit var binding: VDB

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView<VDB>(this, initLayoutResID())

        val arguments = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        val bvmClass: Class<BVM> = arguments[1] as Class<BVM>
        val brClass: Class<BR> = arguments[2] as Class<BR>

        viewModel = ViewModelProviders.of(this, object : ViewModelProvider.NewInstanceFactory() {
            override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
                return bvmClass.getConstructor(brClass).newInstance(brClass.newInstance()) as VM
            }
        }).get(bvmClass)

        super.onCreate(savedInstanceState)
    }
}