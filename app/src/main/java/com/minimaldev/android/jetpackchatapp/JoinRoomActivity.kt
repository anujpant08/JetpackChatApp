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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.minimaldev.android.jetpackchatapp.model.MessageText
import com.minimaldev.android.jetpackchatapp.ui.theme.JetpackChatAppTheme
import java.math.BigInteger
import java.security.SecureRandom

class JoinRoomActivity : AppCompatActivity() {
    private val TAG: String = "JoinRoomActivity"
    private lateinit var db: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.database
        setContent {
            JetpackChatAppTheme {
                SetJoinRoomScreen()
            }
        }
    }
    @Composable
    fun SetJoinRoomScreen(){
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
                text = "Join a Jetpack room.",
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
                text = "Enter the room name.",
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
                        //Join a room logic.
                        val valueEventListener = object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if(dataSnapshot.value != null){
                                    val intent = Intent(context, HomePageActivity::class.java)
                                    intent.putExtra("roomName", roomName.trim())
                                    context.startActivity(intent)
                                }else{
                                    Toast.makeText(
                                        context,
                                        "\'$roomName\' room doesn't exist.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            override fun onCancelled(databaseError: DatabaseError) {
                                Log.e(TAG, "An exception occurred while reading data: ", databaseError.toException())
                            }
                        }
                        db.reference.child("messages").child(roomName).addListenerForSingleValueEvent(valueEventListener)
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