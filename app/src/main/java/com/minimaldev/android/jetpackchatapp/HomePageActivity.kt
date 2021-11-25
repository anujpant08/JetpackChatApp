package com.minimaldev.android.jetpackchatapp

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.minimaldev.android.jetpackchatapp.model.MessageText
import com.minimaldev.android.jetpackchatapp.ui.theme.JetpackChatAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class HomePageActivity : AppCompatActivity() {
    var messages : SnapshotStateList<MessageText> = mutableStateListOf()
    private lateinit var auth: FirebaseAuth
    private lateinit var roomName : String
    val TAG : String = "HomePageActivity"
    private lateinit var db: FirebaseDatabase
    private var totalMessages = 0
    lateinit var mediaPlayer : MediaPlayer
    /*fun HomePageActivity(messages : MutableList<MessageText>){
        this.messages = messages
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomName = intent.getStringExtra("roomName").toString()
        db = Firebase.database
        mediaPlayer = MediaPlayer.create(this, R.raw.received)
        //createDummyMessages()
        val messageRef = db.reference.child("messages").child(roomName)
        val valueEventListener = object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //totalMessages = 0
                messages.clear()
                for(snapshot in dataSnapshot.children){
                    //Log.e(TAG, "Message from DB: $snapshot.value")
                    val map : HashMap<String, Any> = snapshot.value!! as HashMap<String, Any>
                    Log.e(TAG, "map: $map")
                    val newMsg = MessageText(text = map["text"].toString(), name = map["name"].toString())
                    messages.add(newMsg)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "An exception occurred while reading data: ", databaseError.toException())
            }
        }
        Log.e(TAG, "Message(s) from DB: $messages")
        messageRef.addValueEventListener(valueEventListener)
        auth = Firebase.auth
        setContent {
            JetpackChatAppTheme {
                HomePageScreen()
            }
        }
    }
    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun HomePageScreen(){
        var newMessage : String by remember { mutableStateOf("") }
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize(1f)
        ) {
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
                        .fillMaxWidth(1f)
                        .weight(2.4f, true),
                    contentPadding = PaddingValues(8.dp),
                    state = listState
                ){
                    items(
                        items = messages
                    ){
                            message -> MessageBubble(messageText = message)
                        // Scrolling to bottom using coroutine thread whenever a new message is received.
                        // TODO: Use floating button to show new messages instead of scrolling to bottom
                        coroutineScope.launch {
                            if(listState.layoutInfo.totalItemsCount != totalMessages){
                                if(totalMessages == 0 || listState.firstVisibleItemIndex > listState.layoutInfo.totalItemsCount/2){
                                    listState.animateScrollToItem(listState.layoutInfo.totalItemsCount - 1)
                                    if(totalMessages != 0){
                                        mediaPlayer.start()
                                    }
                                }
                                totalMessages = listState.layoutInfo.totalItemsCount
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .weight(0.3f, true),
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
                        Log.e(TAG, "New message is: " + newMessage.trim())
                        if(newMessage.trim() != ""){
                            val messageText = MessageText(text = newMessage, name = auth.currentUser?.displayName.toString(), date = Date())
                            db.reference.child("messages").child(roomName).push().setValue(messageText)
                            messages.add(messageText)
                            coroutineScope.launch {
                                listState.animateScrollToItem(listState.layoutInfo.totalItemsCount - 1)
                            }
                            newMessage = ""
                        }
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
            val showButton by remember {
                //Only show "Scroll to bottom" button when the total messages is > 10 and first visible item position < totalItems/2 and list is not scrolling.
                derivedStateOf {
                    listState.layoutInfo.totalItemsCount > 10 && listState.firstVisibleItemIndex < listState.layoutInfo.totalItemsCount/2
                            && !listState.isScrollInProgress
                }
            }
            AnimatedVisibility(
                visible = showButton,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                totalMessages = listState.layoutInfo.totalItemsCount
                ScrollToBottomButton(coroutineScope, listState)
            }
        }
    }

    @Composable
    private fun ScrollToBottomButton(coroutineScope: CoroutineScope, listState: LazyListState) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 100.dp)
        ){
            ExtendedFloatingActionButton(
                text =
                {
                    Text(
                        text = "Latest messages",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.purple_200_button))
                },
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(listState.layoutInfo.totalItemsCount - 1)
                    }
                },
                backgroundColor = colorResource(id = R.color.new_message_background),
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    focusedElevation = 0.dp,
                    hoveredElevation = 0.dp,
                    pressedElevation = 3.dp
                ),
                modifier = Modifier.height(30.dp)
            )
        }
    }
    override fun onDestroy() {
        mediaPlayer.release() // Releasing resources for preventing memory leak.
        super.onDestroy()
    }
}