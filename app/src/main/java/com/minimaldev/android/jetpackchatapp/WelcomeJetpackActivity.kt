package com.minimaldev.android.jetpackchatapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.ImagePainter.State.Empty.painter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.minimaldev.android.jetpackchatapp.ui.theme.JetpackChatAppTheme

class WelcomeJetpackActivity  : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackChatAppTheme {
                SetJetPackLayout()
            }
        }
    }
    @Composable
    fun SetJetPackLayout(){
        val context = LocalContext.current
        val painter = rememberImagePainter(
            data = AppCompatResources.getDrawable(this,R.drawable.jetchat_background),
            builder = {
                scale(Scale.FILL)
            },
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                Image(
                    painter = painter,
                    contentDescription = "Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ){
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .height(150.dp)
                    )
                    Text(
                        text = "Welcome to Jetpack!",
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
                    TextButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.padding(16.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = colorResource(id = R.color.purple_200_light),
                            contentColor = colorResource(id = R.color.white)
                        ),
                        contentPadding = PaddingValues(all = 12.dp),
                    ) {
                        Text(
                            text = "Join room",
                            fontSize = 28.sp
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .height(8.dp)
                    )
                    TextButton(
                        onClick = {
                                  context.startActivity(Intent(context, CreateRoomActivity::class.java))
                        },
                        modifier = Modifier.padding(16.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = colorResource(id = R.color.purple_200_button),
                            contentColor = colorResource(id = R.color.white)
                        ),
                        contentPadding = PaddingValues(all = 12.dp)
                    ) {
                        Text(
                            text = "New room",
                            fontSize = 28.sp
                        )
                    }
                }
            }
        }
    }
    @Preview
    @Composable
    fun PreviewLayout(){
        setContent {
            JetpackChatAppTheme {
                SetJetPackLayout()
            }
        }
    }
}