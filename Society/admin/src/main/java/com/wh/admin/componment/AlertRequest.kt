package com.wh.admin.componment

import androidx.compose.foundation.layout.Row
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class AlertRequest {

    private var showOneBtnDialog by mutableStateOf(false)
    private var showTwoBtnDialog by mutableStateOf(false)
    private var showXBtnDialog by mutableStateOf(false)
    private var title = "alert"
    private var text: @Composable () -> Unit = {}
    private var onOk: () -> Unit = {}
    private var onNo: () -> Unit = {}
    private var btnX = emptyMap<String, () -> Unit>()

    @Composable
    fun Alert1() {
        if (showOneBtnDialog) {
            AlertDialog(
                onDismissRequest = { showOneBtnDialog = false },
                title = { Text(text = title) },
                text = text,
                confirmButton = {
                    TextButton(onClick = {
                        showOneBtnDialog = false
                        onOk.invoke()
                    }) {
                        Text(text = "确认")
                    }
                }
            )
        }
    }

    @Composable
    fun Alert2() {
        if (showTwoBtnDialog) {
            AlertDialog(
                onDismissRequest = { showTwoBtnDialog = false },
                title = { Text(text = title) },
                text = text,
                confirmButton = {
                    TextButton(onClick = {
                        showTwoBtnDialog = false
                        onOk.invoke()
                    }) {
                        Text(text = "确认")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showTwoBtnDialog = false
                        onNo.invoke()
                    }) {
                        Text(text = "取消")
                    }
                }
            )
        }
    }

    @Composable
    fun AlertX() {
        if (showXBtnDialog) {
            AlertDialog(
                onDismissRequest = { showXBtnDialog = false },
                title = { Text(text = title) },
                text = text,
                buttons = {
                    Row {
                        btnX.entries.forEach {
                            TextButton(onClick = {
                                showXBtnDialog = false
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

    fun show1BtnAlert(title: String, text: String, onOk: () -> Unit) {
        this.title = title
        this.text = { Text(text = text) }
        this.onOk = onOk
        showOneBtnDialog = true
    }

    fun show1BtnAlert(title: String, text: @Composable () -> Unit, onOk: () -> Unit) {
        this.title = title
        this.text = text
        this.onOk = onOk
        showOneBtnDialog = true
    }

    fun show2BtnAlert(
        title: String,
        text: String,
        onOk: () -> Unit,
        onNo: () -> Unit = {}
    ) {
        this.title = title
        this.text = { Text(text = text) }
        this.onOk = onOk
        this.onNo = onNo
        showTwoBtnDialog = true
    }

    fun show2BtnAlert(
        title: String,
        text: @Composable () -> Unit,
        onOk: () -> Unit,
        onNo: () -> Unit = {}
    ) {
        this.title = title
        this.text = text
        this.onOk = onOk
        this.onNo = onNo
        showTwoBtnDialog = true
    }

    fun showXBtnAlert(title: String, text: String, btnX: Map<String, () -> Unit>) {
        this.title = title
        this.text = { Text(text = text) }
        this.btnX = btnX
        showXBtnDialog = true
    }

    fun showXBtnAlert(title: String, text: @Composable () -> Unit, btnX: Map<String, () -> Unit>) {
        this.title = title
        this.text = text
        this.btnX = btnX
        showXBtnDialog = true
    }

    fun alertConfirm(text: String = "要继续吗?", onOk: () -> Unit) {
        this.title = "提示"
        this.text = { Text(text = text) }
        this.onOk = onOk
        showOneBtnDialog = true
    }
}