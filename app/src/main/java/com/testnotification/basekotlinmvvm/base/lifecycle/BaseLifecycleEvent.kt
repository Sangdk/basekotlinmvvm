package com.testnotification.basekotlinmvvm.base.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

abstract class BaseLifecycleEvent : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> onCreate()
            Lifecycle.Event.ON_START -> onStart()
            Lifecycle.Event.ON_PAUSE -> onPause()
            Lifecycle.Event.ON_RESUME -> onResume()
            Lifecycle.Event.ON_STOP -> onStop()
            Lifecycle.Event.ON_DESTROY -> onDestroy()
            Lifecycle.Event.ON_ANY -> onAny()
        }
    }

    protected fun onAny() {
    }

    protected fun onDestroy() {
    }

    protected fun onStop() {
    }

    protected fun onResume() {
    }

    protected fun onPause() {
    }

    protected fun onStart() {
    }

    protected fun onCreate() {}

}