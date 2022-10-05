package com.testnotification.basekotlinmvvm.base.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.testnotification.basekotlinmvvm.utils.exts.inflateViewBinding
import com.testnotification.basekotlinmvvm.utils.exts.registerEventBusBy
import com.testnotification.basekotlinmvvm.utils.exts.unRegisterEventBus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class BaseFragment<VB : ViewBinding> : Fragment(), BaseAndroidComponent<VB> {

    override lateinit var binding: VB

    override val needToSubscribeEventBus = false

    override var safetyScope: CoroutineScope? = null

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

    override fun getViewBinding(parent: ViewGroup?): VB {
        return inflateViewBinding(layoutInflater, parent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(container)
        return binding.root
    }

    override fun VB.initView(savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val consumed = onBackPress()
                    if (!consumed) {
                        isEnabled = true
                        activity?.onBackPressed()
                    }
                }
            }
        )
    }

    override fun onBackPress() = false
}