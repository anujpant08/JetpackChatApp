package com.minimaldev.android.jetpackchatapp.model

import java.util.*

data class Message(
    val text : String = "",
    val name : String = "",
    val date : Date = Date()
)