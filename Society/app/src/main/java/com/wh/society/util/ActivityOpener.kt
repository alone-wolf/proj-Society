package com.wh.society.util

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.uriToImageMultiPartBodyPart

object ActivityOpener {

    fun imagePickerActivity(
        activity: AppCompatActivity,
        requestHolder: RequestHolder
    ): ActivityResultLauncher<String> {
        return activity.registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
                val imageBodyPart = it.uriToImageMultiPartBodyPart(requestHolder.myContentResolver)
                requestHolder.imagePicker.afterImagePick(imageBodyPart)
            }
        }
    }


}