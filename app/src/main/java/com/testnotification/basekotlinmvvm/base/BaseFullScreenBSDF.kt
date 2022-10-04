package com.testnotification.basekotlinmvvm.base

import androidx.viewbinding.ViewBinding

class BaseFullScreenBSDF<VB : ViewBinding> : BaseBSDFragment<VB>() {

    override fun getDefaultDialogHeight(): Int {
        return AppPreferences.screenHeight
    }
}