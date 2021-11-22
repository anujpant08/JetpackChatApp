package com.minimaldev.android.jetpackchatapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.minimaldev.android.jetpackchatapp.LoginActivity.Companion.TAG
import com.minimaldev.android.jetpackchatapp.LoginActivity.Companion.auth
import com.minimaldev.android.jetpackchatapp.LoginActivity.Companion.context
import com.minimaldev.android.jetpackchatapp.LoginActivity.Companion.sendMail
import com.minimaldev.android.jetpackchatapp.ui.theme.JetpackChatAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.security.SecureRandom

class LoginActivity : ComponentActivity() {
    companion object {
        lateinit var auth: FirebaseAuth
        val TAG: String = "MainActivity"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        val sendMail: SendMail = SendMail()
        fun updateUI(success: Boolean, message: String, emailId: String, code: String) {
            Log.e(TAG, "Got the message: " + message)
            if (success) {
                //Start new activity to enter verification code.
                var intent: Intent = Intent(context, VerifyCodeActivity::class.java)
                intent.putExtra("verifyCode", code)
                context.startActivity(intent)
            } else {
                //Show error message
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        context = this
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //Show homepage.
            context.startActivity(Intent(context, HomePageActivity::class.java))
        } else {
            setContent {
                JetpackChatAppTheme {
                    SignInScreen()
                }
            }
        }
    }
}

@Composable
fun SignInScreen() {
    var emailId: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }
    val context = LocalContext.current
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
                .height(16.dp)
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
            visualTransformation = PasswordVisualTransformation(mask = '\u2022'), // masking Bullet (\u2022) char for password field.
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
                if (emailId.trim() != "" && password.trim() != "") {
                    signIn(emailId, password)
                } else {
                    Toast.makeText(
                        LoginActivity.context,
                        "Please enter all the details.",
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
                text = "LOGIN",
                fontSize = 16.sp
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(8.dp)
        )
        Text(
            text = "Not a user? Sign up here.",
            modifier = Modifier.clickable(
                onClick = {
                    context.startActivity(Intent(context, SignUpActivity::class.java))
                }
            ),
            color = colorResource(id = R.color.purple_200_dark),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(8.dp)
        )
        Text(
            text = "Forgot your password?",
            modifier = Modifier.clickable(
                onClick = {
                    if (emailId.trim() != "" && emailId.contains(Regex(""))) {
                        val random = SecureRandom() // Using SHA1PRNG algorithm technique.
                        val randomCode: String = BigInteger(30, random).toString(32).uppercase()
                        CoroutineScope(Dispatchers.IO).launch {
                            sendMail.send(emailId, randomCode, context)
                        }
                        Toast.makeText(
                            LoginActivity.context,
                            "Sending password verification code to $emailId",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        Toast.makeText(
                            LoginActivity.context,
                            "Please enter a valid email address.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            ),
            color = colorResource(id = R.color.purple_200_dark),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}

private fun signIn(email: String, password: String) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                Log.e(TAG, "signInWithEmail:success user-> " + user)
            } else {
                Log.e(TAG, "signInWithEmail:failure", task.exception)
                Toast.makeText(
                    context,
                    "Authentication failed. Please check the email id/password.",
                    Toast.LENGTH_SHORT
                ).show()
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