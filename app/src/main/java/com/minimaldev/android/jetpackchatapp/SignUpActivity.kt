package com.minimaldev.android.jetpackchatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var username by remember { mutableStateOf("") }
        var emailId by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.purple_200_background)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sign up for JetChat.",
                color = colorResource(id = R.color.white),
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(16.dp)
            )
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
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
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(8.dp)
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
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
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(8.dp)
            )
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
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
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(8.dp)
            )
            OutlinedTextField(
                value = emailId,
                onValueChange = { emailId = it },
                label = { Text("Email ID") },
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
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(8.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
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
                ),
                visualTransformation = PasswordVisualTransformation(mask = '\u2022')
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(8.dp)
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
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
                ),
                visualTransformation = PasswordVisualTransformation(mask = '\u2022')
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(8.dp)
            )
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
                    text = "SIGN UP",
                    fontSize = 16.sp
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ShowPreview(){
        JetpackChatAppTheme {
            SignUpScreen()
        }
    }
}