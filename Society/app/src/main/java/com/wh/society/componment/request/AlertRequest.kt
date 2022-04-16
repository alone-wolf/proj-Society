package com.wh.society.componment.request

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

abstract class AlertRequest(private val resources: Resources) {
    val show2BtnDialog: MutableState<Boolean> = mutableStateOf(false)
    val show1BtnDialog: MutableState<Boolean> = mutableStateOf(false)
    val showXBtnDialog: MutableState<Boolean> = mutableStateOf(false)
    var title: String = ""
    var content: @Composable () -> Unit = {}
    var onOKAction: () -> Unit = {}
    var onCancelAction: () -> Unit = {}
    var btns: Map<String, () -> Unit> = emptyMap()


    fun alert(
        title: String,
        content: @Composable () -> Unit,
        onOk: () -> Unit,
    ) {
        this.title = title
        this.content = content
        this.onOKAction = onOk
        this.show1BtnDialog.value = true
    }

    fun alert(
        title: String,
        content: @Composable () -> Unit,
        onOk: () -> Unit,
        onCancel: () -> Unit
    ) {
        this.title = title
        this.content = content
        this.onOKAction = onOk
        this.onCancelAction = onCancel
        this.show2BtnDialog.value = true
    }

    fun alert(title: String, content: String, onOk: () -> Unit, onCancel: () -> Unit = {}) {
        alert(title, { Text(text = content) }, onOk, onCancel)
    }

    fun alert(title: String, content: String, onOk: () -> Unit) {
        alert(title, { Text(text = content) }, onOk)
    }

    fun alert(
        @StringRes title: Int,
        @StringRes content: Int,
        onOk: () -> Unit,
        onCancel: () -> Unit = {}
    ) {
        alert(resources.getString(title), resources.getString(content), onOk, onCancel)
    }

    fun alert(
        @StringRes title: Int,
        @StringRes content: Int,
        onOk: () -> Unit = {},
    ) {
        alert(resources.getString(title), resources.getString(content), onOk)
    }

    fun alert(
        @StringRes title: Int,
        content: @Composable () -> Unit,
        onOk: () -> Unit,
        onCancel: () -> Unit = {}
    ) {
        alert(resources.getString(title), content, onOk, onCancel)
    }

    fun alert(
        @StringRes title: Int,
        content: @Composable () -> Unit,
        onOk: () -> Unit,
    ) {
        alert(resources.getString(title), content, onOk)
    }

    fun alert(
        @StringRes title: Int,
        content: String,
        onOk: () -> Unit,
        onCancel: () -> Unit = {}
    ) {
        alert(resources.getString(title), content, onOk, onCancel)
    }

    fun alert(
        @StringRes title: Int,
        content: String,
        onOk: () -> Unit,
    ) {
        alert(resources.getString(title), content, onOk)
    }

    fun alert(title: String, content: String, btns: Map<String, () -> Unit>) {
        this.title = title
        this.content = { Text(text = content) }
        this.btns = btns
        this.showXBtnDialog.value = true
    }

    fun tip(content: String, onOk: () -> Unit = {}) {
        alert("提示", content, onOk = onOk)
    }

    fun confirm(content: String, onOk: () -> Unit) {
        alert("确认", content, onOk, onCancel = {})
    }
}