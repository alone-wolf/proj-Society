package com.wh.society.componment.request

import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.material.ExperimentalMaterialApi
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import okhttp3.MultipartBody

class ImagePickerRequest(
    private val imagePicker: ActivityResultLauncher<String>,
    private val requestHolder: RequestHolder
) {
    var afterImagePick: (MultipartBody.Part) -> Unit = { i: MultipartBody.Part -> }


    val forUser:()->Unit = {
        afterImagePick = { imageBodyPart ->
            requestHolder.apiViewModel.picCreate(imageBodyPart) {
                requestHolder.apiViewModel.picList()
            }
        }
        imagePicker.launch("image/*")
    }

    fun forUser() {
        afterImagePick = { imageBodyPart ->
            requestHolder.apiViewModel.picCreate(imageBodyPart) {
                requestHolder.apiViewModel.picList()
            }
        }
        imagePicker.launch("image/*")
    }

    @ExperimentalMaterialApi
    fun forSociety() {
        afterImagePick = { imageBodyPart ->
            requestHolder.apiViewModel.societyPictureCreate(
                imageBodyPart,
                requestHolder.trans.society.id
            ) {
                Log.d("WH_ ", "forSociety: ${requestHolder.operatePlatform.currentRoute}")
                Log.d("WH_ ", "forSociety: ${GlobalNavPage.SocietyPictureListPage.route}")
                if (requestHolder.operatePlatform.currentRoute == GlobalNavPage.SocietyPictureListPage.route) {
                    requestHolder.operatePlatform.currentOperate.invoke()
                }
            }
        }
        imagePicker.launch("image/*")
    }

}