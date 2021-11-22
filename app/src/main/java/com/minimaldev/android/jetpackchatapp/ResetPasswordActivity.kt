package com.minimaldev.android.jetpackchatapp

import android.content.Intent
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.minimaldev.android.jetpackchatapp.ui.theme.JetpackChatAppTheme

class ResetPasswordActivity : AppCompatActivity() {
    private val user = Firebase.auth.currentUser!!
    private var emailId : String = ""
    private val TAG : String = "ResetPasswordActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        emailId = intent.getStringExtra("emailId").toString()
        setContent {
            JetpackChatAppTheme {
                SetResetPwdScreen()
            }
        }
    }

    @Composable
    fun SetResetPwdScreen() {
        var password: String by remember { mutableStateOf("") }
        var confirmPassword: String by remember { mutableStateOf("") }
        Column(
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
                text = "Reset password for your account.",
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
                value = password,
                onValueChange = { password = it },
                label = { Text("New password") },
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
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm password") },
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
                    .height(16.dp)
            )
            Button(
                onClick = {
                    if (password == confirmPassword && password.trim() != "") {
                        //Re-authenticate user if currentUser is null
                        if (user != null) {
                            //User is signed in and update password normally.
                            user.updatePassword(password).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        baseContext,
                                        "Password reset successful.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }else if(task.isCanceled){
                                    Toast.makeText(
                                        baseContext,
                                        "Error while resetting password. Please try again.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            //Re-authenticate user to reset password.
                            val credential = EmailAuthProvider.getCredential(emailId, password)
                            user.reauthenticate(credential).addOnSuccessListener {
                                Log.e(TAG, "User re-authenticated.")
                                user.updatePassword(password).addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(
                                            baseContext,
                                            "Password reset successful.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        baseContext.startActivity(Intent(baseContext, WelcomeJetpackActivity::class.java))
                                    }else if(task.isCanceled){
                                        Toast.makeText(
                                            baseContext,
                                            "Error while resetting password. Please try again.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }.addOnFailureListener {
                                Log.e(TAG, "User re-authentication failed." + it)
                            }.addOnCanceledListener {
                                Log.e(TAG, "User re-authentication cancelled.")
                            }
                        }
                    } else if (password != confirmPassword) {
                        Toast.makeText(
                            baseContext,
                            "Password and Confirm Password don't match.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            baseContext,
                            "Please fill all the fields.",
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
                    text = "Submit",
                    fontSize = 16.sp
                )
            }
        }
    }
}