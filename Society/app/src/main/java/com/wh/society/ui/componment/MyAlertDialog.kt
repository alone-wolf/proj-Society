package com.wh.society.ui.componment

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.wh.society.R
import com.wh.society.componment.RequestHolder

@Composable
fun MyAlertDialog(alertRequest: RequestHolder.AlertRequest) {
    if (alertRequest.show2BtnDialog.value) {
        AlertDialog(
            onDismissRequest = { alertRequest.show2BtnDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    alertRequest.onOKAction.invoke()
                    alertRequest.show2BtnDialog.value = false
                }) {
                    Text(text = stringResource(id = R.string.global_alert_ok))
                }

            },
            dismissButton = {
                TextButton(onClick = {
                    alertRequest.onCancelAction.invoke()
                    alertRequest.show2BtnDialog.value = false
                }) {
                    Text(text = stringResource(id = R.string.global_alert_cancel))
                }

            },
            title = { Text(text = alertRequest.title) },
            text = alertRequest.content
        )
    }
    if (alertRequest.show1BtnDialog.value){
        AlertDialog(
            onDismissRequest = { alertRequest.show1BtnDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    alertRequest.onOKAction.invoke()
                    alertRequest.show1BtnDialog.value = false
                }) {
                    Text(text = stringResource(id = R.string.global_alert_ok))
                }

            },
            title = { Text(text = alertRequest.title) },
            text = alertRequest.content
        )
    }
}