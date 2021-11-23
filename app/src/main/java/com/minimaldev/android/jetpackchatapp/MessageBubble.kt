package com.minimaldev.android.jetpackchatapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minimaldev.android.jetpackchatapp.model.MessageText
import com.minimaldev.android.jetpackchatapp.ui.theme.Shapes

@Composable
fun MessageBubble(
    messageText : MessageText
) {
    Card(
        shape = RoundedCornerShape(40),
        modifier = Modifier.padding(8.dp),
    ) {
        Column(
            modifier = Modifier.background(color = colorResource(id = R.color.purple_200_dark)).padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = messageText.name,
                fontSize = 12.sp,
                color = colorResource(id = R.color.white)
            )
            Text(
                text = messageText.text,
                fontSize = 16.sp,
                color = colorResource(id = R.color.white)
            )
        }
    }
}