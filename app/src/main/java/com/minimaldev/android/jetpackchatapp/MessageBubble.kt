package com.minimaldev.android.jetpackchatapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.minimaldev.android.jetpackchatapp.model.MessageText
import com.minimaldev.android.jetpackchatapp.ui.theme.Shapes

@Composable
fun MessageBubble(
    messageText: MessageText
) {
    val auth = Firebase.auth
    if (auth.currentUser?.displayName == messageText.name){
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalArrangement = Arrangement.End
        ){
            Spacer(
                modifier = Modifier
                    .weight(0.2f, true)
            )
            Card(
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.padding(8.dp).weight(0.8f, false),
            ) {
                SetupChatBubble(auth, messageText)
            }
        }
    }else{
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalArrangement = Arrangement.Start
        ){
            Card(
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.padding(8.dp).weight(0.8f, false),
            ) {
                SetupChatBubble(auth, messageText)
            }
            Spacer(
                modifier = Modifier
                    .weight(0.2f, true)
            )
        }
    }
}

@Composable
private fun SetupChatBubble(
    auth: FirebaseAuth,
    messageText: MessageText
) {
    Column(
        modifier = if (auth.currentUser?.displayName == messageText.name) {
            Modifier
                .background(color = colorResource(id = R.color.purple_200_background))
                .padding(10.dp)
        } else {
            Modifier
                .background(color = colorResource(id = R.color.purple_200_dark))
                .padding(10.dp)
        },
        horizontalAlignment = if (auth.currentUser?.displayName == messageText.name) {
            Alignment.End
        } else {
            Alignment.Start
        },
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(
            modifier = Modifier
                .height(4.dp)
        )
        if (auth.currentUser?.displayName != messageText.name){
            Text(
                text = messageText.name,
                fontSize = 12.sp,
                color = colorResource(id = R.color.white),
            )
            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )
        }
        Text(
            text = messageText.text,
            fontSize = 16.sp,
            color = colorResource(id = R.color.white),
            textAlign = if (auth.currentUser?.displayName == messageText.name){
                TextAlign.End
            }else{
                TextAlign.Start
            }
        )
        Spacer(
            modifier = Modifier
                .height(4.dp)
        )
    }
}