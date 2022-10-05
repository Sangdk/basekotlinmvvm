package com.testnotification.basekotlinmvvm.base.repository

import okhttp3.MultipartBody

interface IRetrofitRepoExt {

    fun createTextMultipart(vararg pairs: Pair<String, Any>) =
        MultipartBody.Builder().setType(MultipartBody.FORM).apply {
            for (pair in pairs) {
                addFormDataPart(pair.first, pair.second.toString())
            }
        }.build()
}