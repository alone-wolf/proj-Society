package com.wh.admin.data.impl

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

interface IRequestBody:IJsonObject {
    fun toRequestBody(): RequestBody {
        return toJSONObject().toString(0)
            .toRequestBody("application/json;charset=UTF-8".toMediaTypeOrNull())
    }
}