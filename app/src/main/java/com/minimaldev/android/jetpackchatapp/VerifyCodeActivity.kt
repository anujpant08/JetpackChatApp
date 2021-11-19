package com.minimaldev.android.jetpackchatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minimaldev.android.jetpackchatapp.ui.theme.JetpackChatAppTheme

class VerifyCodeActivity : AppCompatActivity() {
    private var verifyCode : String = ""
    private val TAG : String = "VerifyCodeActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyCode = intent.getStringExtra("verifyCode").toString()
        Log.e(TAG, "verify code is:  " + verifyCode)
        setContent {
            JetpackChatAppTheme {
                SetVerifyScreen()
            }
        }
    }
    @Composable
    fun SetVerifyScreen(){
        var code : String by remember { mutableStateOf("") }
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.purple_200_background)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to JetChat!",
                color = colorResource(id = R.color.white),
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(24.dp)
            )
            Text(
                text = "Enter the verification code.",
                color = colorResource(id = R.color.white),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(16.dp)
            )
            OutlinedTextField(
                value = code,
                onValueChange = { code = it },
                label = { Text("Verification Code") },
                textStyle = TextStyle(
                    color = colorResource(id = R.color.white),
                    fontSize = 16.sp
                ),
                visualTransformation = PasswordVisualTransformation(mask = '\u2022'),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.purple_200_light),
                    unfocusedBorderColor = colorResource(id = R.color.purple_200_dark),
                    focusedLabelColor = colorResource(id = R.color.purple_200_light),
                    unfocusedLabelColor = colorResource(id = R.color.purple_200_dark),
                    cursorColor = colorResource(id = R.color.white)
                )
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(8.dp)
            )
            Button(
                onClick = {
                    if (code.trim() != "") {
                        verifyCode(code)
                    } else {
                        Toast.makeText(
                            LoginActivity.context,
                            "Please enter a valid code.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.padding(12.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = colorResource(id = R.color.purple_200_button),
                    contentColor = colorResource(id = R.color.white)
                )
            ) {
                Text(
                    text = "SUBMIT",
                    fontSize = 16.sp
                )
            }
        }
    }
    private fun verifyCode(code : String){
        if(code == verifyCode){
            //Go to homepage and login is successful
            Toast.makeText(
                this,
                "Verification successful.",
                Toast.LENGTH_SHORT
            ).show()
        }else{
            Toast.makeText(
                this,
                "Verification code is incorrect. Please try again.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    @Preview
    @Composable
    fun SetPreview(){
        setContent {
            JetpackChatAppTheme {
                SetVerifyScreen()
            }
        }
    }
}