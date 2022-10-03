@file:Suppress("UNCHECKED_CAST")
package com.testnotification.basekotlinmvvm.utils.exts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

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