package com.wh.society.util

import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.wh.society.componment.RequestHolder
import com.wh.society.typeExt.contentUriToMultiPartBodyPart

object ActivityOpener {

    fun forUserIconImage(
        activity: AppCompatActivity,
        requestHolder: RequestHolder
    ): ActivityResultLauncher<String> {
        return activity.registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
                Log.d("WH_", "forUserIconImage: $it")
                val imageBodyPart = it.contentUriToMultiPartBodyPart(
                    contentResolver = requestHolder.myContentResolver,
                    mediaType = "image/*",
                    partKey = "file",
                    partFileName = "image"
                )
                requestHolder.apiViewModel.picCreate(imageBodyPart) {
                    requestHolder.apiViewModel.picList()
                }
            }

        }
    }


}