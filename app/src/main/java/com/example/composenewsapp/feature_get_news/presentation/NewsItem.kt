package com.example.composenewsapp.feature_get_news.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageResult
import com.example.composenewsapp.feature_get_news.utils.Screen
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun NewsCard(
    navController: NavController,
    title: String,
    content: String,
    imageUrl: String,
    articleUrl: String
) {
    Card(
        modifier = Modifier
            .clickable {
                val encodedUrl = URLEncoder.encode(articleUrl, StandardCharsets.UTF_8.toString())
                navController.navigate("news_web_view_screen/$encodedUrl")
            }
            .fillMaxWidth()
            .padding(3.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(4.dp)
            )
            NewsImage(url = imageUrl)
            Text(
                text = content,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                modifier = Modifier.padding(4.dp)
            )
        }

    }
}

@Composable
fun NewsImage(url: String) {
    val painter =
        rememberAsyncImagePainter(model = url, contentScale = ContentScale.Crop)
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
        )
        // You can also handle loading state, error state, etc. using painter states
        when (painter.state) {
            is AsyncImagePainter.State.Error -> {
                // Handle error state
                Text(
                    text = "Failed to load image...",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .clickable {

                        }
                )
            }
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator(
                    color = Color.Blue,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                // Handle other states if needed
            }
        }
    }
}

@Composable
@Preview()
fun NewsImagePreview() {
    NewsImage(url = "https://images.indianexpress.com/2023/04/ASA-20230410-Kamakshi_HS.jpg")
}