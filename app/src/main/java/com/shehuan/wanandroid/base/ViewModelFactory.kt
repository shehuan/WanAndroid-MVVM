package com.shehuan.wanandroid.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.shehuan.wanandroid.base.net.BaseRepository
import kotlin.reflect.KClass

/**
 * Author: shehuan
 * Date: 2019/12/12 14:19
 * Description:
 */

/**
 * 在Activity中初始化viewModel
 */
@Suppress("UNCHECKED_CAST")
fun <BVM : BaseViewModel> initViewModel(
    activity: FragmentActivity,
    vmClass: KClass<BVM>,
    rClass: KClass<out BaseRepository>
) =
    ViewModelProviders.of(activity, object : ViewModelProvider.NewInstanceFactory() {
        override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
            return vmClass.java.getConstructor(rClass.java).newInstance(rClass.java.newInstance()) as VM
        }
    }).get(vmClass.java)


/**
 * 在Fragment中初始化viewModel
 */
@Suppress("UNCHECKED_CAST")
fun <BVM : BaseViewModel> initViewModel(
    fragment: Fragment,
    vmClass: KClass<BVM>,
    rClass: KClass<out BaseRepository>
) =
    ViewModelProviders.of(fragment, object : ViewModelProvider.NewInstanceFactory() {
        override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
            return vmClass.java.getConstructor(rClass.java).newInstance(rClass.java.newInstance()) as VM
        }
    }).get(vmClass.java)