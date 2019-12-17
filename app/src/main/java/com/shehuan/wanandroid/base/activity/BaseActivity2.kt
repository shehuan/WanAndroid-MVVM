package com.shehuan.wanandroid.base.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.BaseViewModel
import java.lang.reflect.ParameterizedType

abstract class BaseActivity2<VDB : ViewDataBinding, BVM : BaseViewModel, BR : BaseRepository> :
    BaseActivity() {

    protected lateinit var viewModel: BVM

    protected lateinit var binding: VDB

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
//        binding = DataBindingUtil.setContentView<VDB>(this, initLayoutResID())

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