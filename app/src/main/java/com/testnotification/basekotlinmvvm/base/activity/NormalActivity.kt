package com.testnotification.basekotlinmvvm.base.activity

import android.view.WindowManager
import androidx.viewbinding.ViewBinding

class NormalActivity<VB : ViewBinding> : BaseActivity<VB>() {
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val params: WindowManager.LayoutParams = window.attributes
        @Suppress("DEPRECATION")
        params.flags = params.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
        window.attributes = params
        window.clearFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}