package com.testnotification.basekotlinmvvm.base.component

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.viewbinding.ViewBinding
import com.testnotification.basekotlinmvvm.utils.exts.inflateViewBinding
import com.testnotification.basekotlinmvvm.utils.exts.layoutInflater

abstract class BaseDialog<VB : ViewBinding>(protected val context: Context) {
    companion object {
        val instanceManager by lazy { hashMapOf<String, Boolean>() }
    }

    protected val binding by lazy { inflateViewBinding<VB>(context.layoutInflater) }

    private val builder by lazy {
        AlertDialog.Builder(context).apply {
            setView(binding.root)
            binding.initView()
        }
    }

    private val dialog by lazy {
        builder.create().apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setOnDismissListener {
                instanceManager.remove(this@BaseDialog.javaClass.name)
            }
        }
    }

    fun dismiss() {
        dialog.dismiss()
    }

    fun setCancelable(cancelable: Boolean) {
        dialog.setCancelable(cancelable)
    }

    fun show() {
        val name = javaClass.name
        if (instanceManager[name] == true) return
        instanceManager[name] = true
        dialog.show()
    }

    protected abstract fun VB.initView()

}