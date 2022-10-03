package com.testnotification.basekotlinmvvm.utils.exts

import org.greenrobot.eventbus.EventBus

fun Any.registerEventBusBy(needToSubscribe: Boolean = true) {
    if (!EventBus.getDefault().isRegistered(this) && needToSubscribe) {
        EventBus.getDefault().register(this)
    }
}

fun Any.unRegisterEventBus() {
    if (EventBus.getDefault().isRegistered(this)) {
        EventBus.getDefault().unregister(this)
    }
}