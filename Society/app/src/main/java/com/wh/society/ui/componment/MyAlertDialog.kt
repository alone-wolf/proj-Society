package com.wh.society.ui.componment

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wh.society.R
import com.wh.society.componment.RequestHolder
import com.wh.society.componment.request.AlertRequest

@Composable
fun MyAlertDialog(alertRequest: AlertRequest) {
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
                }) { Text(text = stringResource(id = R.string.global_alert_cancel)) }

            },
            title = { Text(text = alertRequest.title) },
            text = alertRequest.content
        )
    }
    if (alertRequest.show1BtnDialog.value) {
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
    if (alertRequest.showXBtnDialog.value) {
        AlertDialog(
            onDismissRequest = { alertRequest.showXBtnDialog.value = false },
            title = { Text(text = alertRequest.title) },
            text = alertRequest.content,
            buttons = {
                Row(modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)) {
                    alertRequest.btns.entries.forEach {
                        TextButton(onClick = {
                            alertRequest.showXBtnDialog.value = false
                            it.value.invoke()
                        }) {
                            Text(text = it.key)
                        }
                    }
                }
            }
        )
    }
}