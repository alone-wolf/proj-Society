package com.wh.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wh.admin.componment.RequestHolder
import com.wh.admin.store.SettingStore
import com.wh.admin.ui.theme.SocietyTheme

class MainActivity : ComponentActivity(), RequestHolder {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocietyTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                }
            }
        }
    }

    override val settingStore: SettingStore by lazy {
        SettingStore(this)
    }
}