package com.minimaldev.android.jetpackchatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minimaldev.android.jetpackchatapp.model.MessageText
import com.minimaldev.android.jetpackchatapp.ui.theme.JetpackChatAppTheme
import java.time.Instant.now
import java.util.*

class HomePageActivity : AppCompatActivity() {
    var messages : MutableList<MessageText> = mutableListOf()
    lateinit var roomName : String
    /*fun HomePageActivity(messages : MutableList<MessageText>){
        this.messages = messages
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomName = intent.getStringExtra("roomName").toString()
        createDummyMessages()
        setContent {
            JetpackChatAppTheme {
                HomePageScreen()
            }
        }
    }
    @Composable
    fun HomePageScreen(){
        var newMessage : String by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.white))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(color = colorResource(id = R.color.purple_200_dark))
                    .weight(0.3f, true)
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center,
            ){
                Text(
                    text = roomName,
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.white),
                    fontWeight = FontWeight.SemiBold
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(1.5f)
                    .weight(2.3f, true),
                contentPadding = PaddingValues(8.dp),
            ){
                items(
                    items = messages
                ){
                    message -> MessageBubble(messageText = message)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(0.4f, true),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = newMessage,
                    onValueChange = { newMessage = it },
                    modifier = Modifier
                        .background(
                            color = colorResource(id = R.color.new_message_background),
                            shape = RoundedCornerShape(14.dp)
                        )
                        .weight(1f, true)
                        .height(40.dp)
                        .padding(8.dp),
                    textStyle = TextStyle(
                        color = colorResource(id = R.color.black),
                        fontSize = 16.sp
                    ),
                )
                IconButton(onClick = {
                    //TODO: Send message and update in Firebase.

                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.send),
                        contentDescription = "Send",
                        modifier = Modifier
                            .size(32.dp)
                            .weight(0.3f, true),
                        tint = colorResource(id = R.color.purple_200_dark)
                    )
                }
            }
        }
    }
    private fun createDummyMessages(){
        var messageText1 = MessageText("Hi!", "Kevin", Date())
        var messageText2 = MessageText("Hello!", "John", Date())
        var messageText3 = MessageText("How are you ?", "Kevin", Date())
        var messageText4 = MessageText("I am good, thanks!", "John", Date())
        var messageText5 = MessageText("Great!", "Amy", Date())
        this.messages.add(messageText1)
        this.messages.add(messageText2)
        this.messages.add(messageText3)
        this.messages.add(messageText4)
        this.messages.add(messageText5)
    }
}