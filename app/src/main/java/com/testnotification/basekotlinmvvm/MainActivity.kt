package com.testnotification.basekotlinmvvm

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.testnotification.basekotlinmvvm.base.component.activity.NormalActivity
import com.testnotification.basekotlinmvvm.databinding.ActivityMainBinding
import com.testnotification.basekotlinmvvm.utils.AppPreferences
import com.testnotification.basekotlinmvvm.utils.exts.doOnViewDrawn

class MainActivity : NormalActivity<ActivityMainBinding>() {
    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun ActivityMainBinding.initView(savedInstanceState: Bundle?) {
        viewHeightCalculate.doOnViewDrawn {
            AppPreferences.screenWidth = viewHeightCalculate.measuredWidth
            AppPreferences.screenHeight = viewHeightCalculate.measuredHeight
        }
    }
}