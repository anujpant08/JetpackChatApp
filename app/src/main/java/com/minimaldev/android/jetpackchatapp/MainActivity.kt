package com.minimaldev.android.jetpackchatapp

import android.graphics.Color
import android.icu.lang.UCharacter
import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.EditCommand
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minimaldev.android.jetpackchatapp.ui.theme.JetpackChatAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackChatAppTheme{
                SignInScreen()
            }
        }
    }
}
@Composable
fun SignInScreen() {
    var username by remember{ mutableStateOf("")}
    var password by remember{ mutableStateOf("")}
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.purple_200_background)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ){
        Text(text = "Welcome to JetChat!",
            color = colorResource(id = R.color.white),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier
            .fillMaxWidth(1f)
            .height(16.dp))
        OutlinedTextField(
            value = username,
            onValueChange = {username = it},
            label = {Text("Username")},
            textStyle = TextStyle(
                color = colorResource(id = R.color.white),
                fontSize = 16.sp
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.purple_200_light),
                unfocusedBorderColor = colorResource(id = R.color.purple_200_dark),
                focusedLabelColor = colorResource(id = R.color.purple_200_light),
                unfocusedLabelColor = colorResource(id = R.color.purple_200_dark),
                cursorColor = colorResource(id = R.color.white)
            )
        )
        Spacer(modifier = Modifier
            .fillMaxWidth(1f)
            .height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = {Text("Password")},
            textStyle = TextStyle(
                color = colorResource(id = R.color.white),
                fontSize = 16.sp
            ),
            visualTransformation = PasswordVisualTransformation(mask = '\u2022'), // masking Bullet (\u2022) char for password field.
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.purple_200_light),
                unfocusedBorderColor = colorResource(id = R.color.purple_200_dark),
                focusedLabelColor = colorResource(id = R.color.purple_200_light),
                unfocusedLabelColor = colorResource(id = R.color.purple_200_dark),
                cursorColor = colorResource(id = R.color.white)
            )
        )
        Spacer(modifier = Modifier
            .fillMaxWidth(1f)
            .height(16.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(12.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = colorResource(id = R.color.purple_200_button),
                contentColor = colorResource(id = R.color.white)
            )
        ) {
            Text(
                text = "LOGIN",
                fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackChatAppTheme {
        SignInScreen()
    }
}