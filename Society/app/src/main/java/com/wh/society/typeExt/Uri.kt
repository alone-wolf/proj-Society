package com.wh.society.typeExt

import android.content.ContentResolver
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

fun Uri.contentUriToMultiPartBodyPart(
    contentResolver: ContentResolver,
    mediaType: String,
    partKey: String,
    partFileName: String
): MultipartBody.Part {
    val inputBytes = contentResolver.openInputStream(this)!!.bytes()

    val requestFile = inputBytes.toRequestBody(
        contentType = mediaType.toMediaTypeOrNull(),
        offset = 0,
        byteCount = inputBytes.size
    )
    return MultipartBody.Part.createFormData(partKey, partFileName, requestFile)

}

fun Uri.uriToImageMultiPartBodyPart(contentResolver: ContentResolver): MultipartBody.Part {
    return contentUriToMultiPartBodyPart(
        contentResolver = contentResolver,
        mediaType = "image/*",
        partKey = "file",
        partFileName = "image"
    )
}