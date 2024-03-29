package com.testnotification.basekotlinmvvm.base.component

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.testnotification.basekotlinmvvm.R
import com.testnotification.basekotlinmvvm.utils.AppPreferences
import com.testnotification.basekotlinmvvm.utils.exts.findFragment
import com.testnotification.basekotlinmvvm.utils.exts.inflateViewBinding
import com.testnotification.basekotlinmvvm.utils.exts.registerEventBusBy
import com.testnotification.basekotlinmvvm.utils.exts.unRegisterEventBus
import com.testnotification.basekotlinmvvm.utils.listener.SimpleBottomSheetCallBackListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class BaseBSDFragment<VB : ViewBinding> : BottomSheetDialogFragment(),
    BaseAndroidComponent<VB> {

    override var safetyScope: CoroutineScope? = null

    override lateinit var binding: VB

    override val needToSubscribeEventBus = false

    override fun getTheme() = R.style.SheetDialog

    protected open fun getDefaultDialogHeight(): Int {
        return 0
    }

    protected open fun isDraggable() = true

    override fun onStart() {
        super.onStart()
        safetyScope = CoroutineScope(Dispatchers.IO)
        registerEventBusBy(needToSubscribeEventBus)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.root.layoutParams.apply {
            height = AppPreferences.screenHeight
            binding.root.layoutParams = this
        }
        binding.initView(savedInstanceState)
        binding.setupViewModel()
        binding.initListener()
    }

    override fun VB.initView(savedInstanceState: Bundle?) {}

    override fun VB.setupViewModel() {}

    override fun VB.initListener() {}

    override fun getViewBinding(parent: ViewGroup?): VB {
        return inflateViewBinding(layoutInflater, parent)
    }

    @CallSuper
    open fun show(manager: FragmentManager) {
        val bsdf = manager.findFragment(this) ?: this
        if (bsdf is BottomSheetDialogFragment && !bsdf.isAdded) {
            super.show(manager, javaClass.simpleName)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).apply {
            val defaultHeight = getDefaultDialogHeight()
            this.setOnShowListener {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val frameLayout =
                    this.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                        ?: return@setOnShowListener
                if (defaultHeight > 0) {
                    frameLayout.layoutParams.apply {
                        height = defaultHeight
                        frameLayout.layoutParams = this
                        frameLayout.requestLayout()
                    }
                }
                val behavior = from(frameLayout)
                behavior.isDraggable = isDraggable()
                behavior.state = STATE_EXPANDED
                behavior.peekHeight = defaultHeight
                behavior.isHideable = true
                behavior.addBottomSheetCallback(object : SimpleBottomSheetCallBackListener() {
                    @SuppressLint("SwitchIntDef")
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        when (newState) {
                            STATE_COLLAPSED -> behavior.state = STATE_EXPANDED
                            STATE_DRAGGING -> if (!isDraggable()) behavior.state = STATE_EXPANDED
                            STATE_HIDDEN -> dismiss()
                        }
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        safetyScope?.cancel()
        unRegisterEventBus()
    }


}