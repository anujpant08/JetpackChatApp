package com.minimaldev.android.jetpackchatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.minimaldev.android.jetpackchatapp.ui.theme.JetpackChatAppTheme

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackChatAppTheme {
                SignUpScreen()
            }
        }
    }

    @Composable
    fun SignUpScreen(){

    }
}