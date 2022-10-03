package com.testnotification.basekotlinmvvm.base

import android.content.Context
import androidx.viewbinding.ViewBinding

class BaseDialog<VB : ViewBinding>(protected val context: Context) {
    companion object {
        val instanceManager by lazy { hashMapOf<String, Boolean>() }
    }

}