package com.shehuan.wanandroid.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.shehuan.wanandroid.base.net.BaseRepository
import com.shehuan.wanandroid.base.BaseViewModel
import java.lang.reflect.ParameterizedType

abstract class BaseFragment2<VDB : ViewDataBinding, BVM : BaseViewModel, BR : BaseRepository> :
    BaseFragment() {

    protected lateinit var viewModel: BVM

    protected lateinit var binding: VDB

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(initLayoutResID(), container, false)
        binding = DataBindingUtil.bind<VDB>(view)!!
        return view
    }
}