package com.testnotification.basekotlinmvvm.base.repository

import android.content.Context
import androidx.room.Room
import java.lang.reflect.ParameterizedType

abstract class RoomRepository<T : BaseRoomDatabase<DAO>, DAO>(
    private val context: Context,
    private val databaseName: String
) {
    companion object {
        val instanceMap by lazy { hashMapOf<String, RoomRepository<*, *>>() }

        inline fun <reified T : RoomRepository<*, *>> instantiate(context: Context): T =
            (instanceMap[T::class.java.name] as T?)
                ?: T::class.java.getConstructor(Context::class.java)
                    .newInstance(context).apply {
                        instanceMap[T::class.java.name] = this
                    }

        inline fun <reified T : RoomRepository<*, *>> instantiate(
            clazz: Array<Class<*>>,
            params: Array<*>
        ): RoomRepository<*, *> =
            instanceMap[T::class.java.name] ?: T::class.java.getConstructor(*clazz)
                .newInstance(*params).apply {
                    instanceMap[T::class.java.name] = this
                }
    }

    private val database by lazy(::createDatabase)

    private fun createDatabase(): T {
        return Room.databaseBuilder(
            context,
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>,
            databaseName
        ).allowMainThreadQueries()
            .build()

    }
}