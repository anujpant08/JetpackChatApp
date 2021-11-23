package com.minimaldev.android.jetpackchatapp.model

import java.util.*

data class MessageText(
    val text : String = "",
    val name : String = "",
    val date : Date = Date()
)