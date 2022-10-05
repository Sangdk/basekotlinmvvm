package com.testnotification.basekotlinmvvm.base.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.testnotification.basekotlinmvvm.R
import com.testnotification.basekotlinmvvm.utils.AppPreferences
import com.testnotification.basekotlinmvvm.utils.exts.findFragment
import com.testnotification.basekotlinmvvm.utils.exts.inflateViewBinding
import com.testnotification.basekotlinmvvm.utils.exts.registerEventBusBy
import com.testnotification.basekotlinmvvm.utils.exts.unRegisterEventBus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlin.math.roundToInt

abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment(), BaseAndroidComponent<VB> {

    override var safetyScope: CoroutineScope? = null

    override lateinit var binding: VB

    override fun isCancelable() = true

    override val needToSubscribeEventBus = false

    override fun getTheme() = R.style.DialogTheme

    open fun matchScreenPercent() = 0.8f

    override fun getViewBinding(parent: ViewGroup?): VB {
        return inflateViewBinding(layoutInflater, parent)
    }

    override fun onStart() {
        super.onStart()
        safetyScope = CoroutineScope(Dispatchers.IO)
        registerEventBusBy(needToSubscribeEventBus)
    }

    override fun onDestroy() {
        super.onDestroy()
        safetyScope?.cancel()
        unRegisterEventBus()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(isCancelable)
        binding = getViewBinding(container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.root.layoutParams.apply {
            width = ((AppPreferences.screenWidth * matchScreenPercent())).roundToInt()
            height = getDefaultDialogHeight().takeIf { it > 0 } ?: WRAP_CONTENT
            binding.root.layoutParams = this
        }
        binding.initView(savedInstanceState)
        binding.setupViewModel()
        binding.initListener()
    }

    override fun VB.initView(savedInstanceState: Bundle?) {}

    override fun VB.setupViewModel() {}

    override fun VB.initListener() {}

    open fun show(manager: FragmentManager) {
        val df = manager.findFragment(this) ?: this
        if (df is DialogFragment && !df.isAdded) {
            super.show(manager, javaClass.simpleName)
        }
    }

    protected fun getDefaultDialogHeight(): Int {
        return 0
    }
}