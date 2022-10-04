package com.testnotification.basekotlinmvvm.base.repository

import androidx.room.RoomDatabase

abstract class BaseRoomDatabase<DAO> : RoomDatabase() {
    abstract fun dao() : DAO
}