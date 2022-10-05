@file:Suppress("UNCHECKED_CAST")

package com.testnotification.basekotlinmvvm.utils.exts

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.viewbinding.ViewBinding
import com.testnotification.basekotlinmvvm.R
import java.lang.reflect.ParameterizedType

fun View.gone() {
    visibility = GONE
}

fun View.visible() {
    visibility = VISIBLE
}

fun View.invisible() {
    visibility = INVISIBLE
}

fun View.visibleBy(visible: Boolean) {
    if (visible) visible() else gone()
}

fun View.clickNoAnim(onClick: (View) -> Unit) {
    if (background == null) setBackgroundResource(R.drawable.ripple_clickable)
    setOnClickListener(onClick)
}

fun View.invisibleBy(invisible: Boolean) {
    if (invisible) invisible() else visible()
}

fun <VB : ViewBinding> Any.inflateViewBinding(
    inflater: LayoutInflater,
    parent: ViewGroup? = null,
    attachToParent: Boolean = false
): VB {
    val clazz =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>
    return clazz.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    ).invoke(null, inflater, parent, attachToParent) as VB
}

fun View.doOnViewDrawn(removeCallBackBy: (() -> Boolean) = { true }, onDrawn: () -> Unit) {
    var global: ViewTreeObserver.OnGlobalLayoutListener? = null
    global = ViewTreeObserver.OnGlobalLayoutListener {
        if (removeCallBackBy()) {
            onDrawn()
            viewTreeObserver.removeOnGlobalLayoutListener(global)
        }
    }
    viewTreeObserver.addOnGlobalLayoutListener(global)
}