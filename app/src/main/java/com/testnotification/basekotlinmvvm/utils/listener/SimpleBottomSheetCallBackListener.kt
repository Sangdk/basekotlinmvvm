package com.testnotification.basekotlinmvvm.utils.listener

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

open class SimpleBottomSheetCallBackListener : BottomSheetBehavior.BottomSheetCallback() {
    override fun onStateChanged(bottomSheet: View, newState: Int) {
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
    }
}