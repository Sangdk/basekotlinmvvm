package com.testnotification.basekotlinmvvm.base.activity

import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.viewbinding.ViewBinding

class BaseFullScreenActivity<VB : ViewBinding> : BaseActivity<VB>() {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.show(WindowInsets.Type.statusBars())
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }
}