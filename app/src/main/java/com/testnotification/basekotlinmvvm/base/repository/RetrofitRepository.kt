package com.testnotification.basekotlinmvvm.base.repository

import com.google.gson.GsonBuilder
import com.testnotification.basekotlinmvvm.utils.Constant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.ParameterizedType
import java.util.concurrent.TimeUnit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

@Suppress("UNCHECKED_CAST")
abstract class RetrofitRepository<T> : IRetrofitRepoExt {

    open fun getBaseUrl() = Constant.BASE_URL

    companion object {
        val repoInstantMap by lazy { hashMapOf<String, RetrofitRepository<*>>() }

        inline fun <reified RP : RetrofitRepository<*>> instantiate(): RP {
            return (repoInstantMap[RP::class.java.simpleName] as RP?)
                ?: RP::class.java.getConstructor().newInstance().apply {
                    repoInstantMap[RP::class.java.simpleName] = this
                }
        }
    }

    val service by lazy(::createService)

    private fun createService(): T {
        val clazz =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        return Retrofit.Builder().baseUrl(getBaseUrl())
            .client(
                OkHttpClient.Builder().callTimeout(7, TimeUnit.SECONDS)
                    .connectTimeout(7, TimeUnit.SECONDS)
                    .readTimeout(7, TimeUnit.SECONDS)
                    .writeTimeout(7, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(clazz)
    }

}