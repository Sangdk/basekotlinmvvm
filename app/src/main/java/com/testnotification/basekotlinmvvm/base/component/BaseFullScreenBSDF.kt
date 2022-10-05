package com.testnotification.basekotlinmvvm.base.component

import androidx.viewbinding.ViewBinding
import com.testnotification.basekotlinmvvm.utils.AppPreferences

class BaseFullScreenBSDF<VB : ViewBinding> : BaseBSDFragment<VB>() {

    override fun getDefaultDialogHeight(): Int {
        return AppPreferences.screenHeight
    }
}