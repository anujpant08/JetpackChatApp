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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.minimaldev.android.jetpackchatapp.ui.theme.JetpackChatAppTheme
import java.math.BigInteger
import java.security.SecureRandom

class CreateRoomActivity : AppCompatActivity() {
    private val TAG: String = "CreateRoomActivity"
    private lateinit var db: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.database
        val messagesRef = db.reference.child("messages")
        setContent {
            JetpackChatAppTheme {
                SetCreateRoomLayout()
            }
        }
    }
    @Composable
    fun SetCreateRoomLayout(){
        val context = LocalContext.current
        var roomName : String by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.purple_200_background)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = "Create a Jetpack room.",
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
            Text(
                text = "Enter a unique room name.",
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
                value = roomName,
                onValueChange = { roomName = it },
                label = { Text("Room Name") },
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
            Button(
                onClick = {
                    if (roomName.trim() != "") {
                        //Create a room unique-code logic.
                        val random = SecureRandom() // Using SHA1PRNG algorithm technique.
                        val randomCode: String = BigInteger(30, random).toString(32).uppercase()
                        val finalRoomName = "JetChat-${roomName.trim()}-$randomCode" // Creating a unique room name.
                        Log.e(TAG, "room name is: $finalRoomName")
                        //Adding new child node in the firebase realtime db tree structure for a new room, with a default message for every new room.
                        //db.reference.child("messages").child(roomName).push().setValue("This is the starting message of this room.")
                        val intent = Intent(context, HomePageActivity::class.java)
                        intent.putExtra("roomName", roomName.trim())
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(
                            context,
                            "Please enter a valid room name.",
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