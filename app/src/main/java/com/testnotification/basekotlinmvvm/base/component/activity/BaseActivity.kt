package com.testnotification.basekotlinmvvm.base.component.activity

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.testnotification.basekotlinmvvm.base.component.BaseAndroidComponent
import com.testnotification.basekotlinmvvm.utils.exts.inflateViewBinding
import com.testnotification.basekotlinmvvm.utils.exts.registerEventBusBy
import com.testnotification.basekotlinmvvm.utils.exts.remove
import com.testnotification.basekotlinmvvm.utils.exts.unRegisterEventBus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), BaseAndroidComponent<VB> {

    override val binding by lazy(::getViewBinding)

    override var safetyScope: CoroutineScope? = CoroutineScope(Dispatchers.IO)

    override fun getViewBinding(parent: ViewGroup?): VB {
        return inflateViewBinding(layoutInflater, parent)
    }

    override val needToSubscribeEventBus = false

    open var onPermissionResult: ((Int, Array<out String>, IntArray) -> Unit)? = null

    open var onActivityResult: ((Intent?) -> Unit)? = null

    override fun onStart() {
        super.onStart()
        registerEventBusBy(needToSubscribeEventBus)
        safetyScope = CoroutineScope(Dispatchers.IO)
    }

    override fun onDestroy() {
        super.onDestroy()
        unRegisterEventBus()
        safetyScope?.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        savedInstanceState?.let { removeAllDialogFragment() }
        binding.initView(savedInstanceState)
        binding.setupViewModel()
        binding.initListener()
    }

    override fun VB.initView(savedInstanceState: Bundle?) {}

    override fun VB.setupViewModel() {}

    override fun VB.initListener() {}

    protected open fun canBackPress() = true

    @CallSuper
    override fun onBackPressed() {
        for (fragment in supportFragmentManager.fragments) {
            if (fragment is BaseAndroidComponent<*>) {
                if (fragment.onBackPress()) {
                    return
                }
            }
        }
        if (canBackPress())
            super.onBackPressed()
    }

    private fun removeAllDialogFragment() {
        supportFragmentManager.apply {
            for (fragment in fragments) {
                if (fragment is DialogFragment && fragment.isAdded) {
                    fragment.dismissAllowingStateLoss()
                }
                remove(fragment)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onPermissionResult?.invoke(requestCode, permissions, grantResults)
    }

    val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                onActivityResult?.invoke(data)
            }
        }
}