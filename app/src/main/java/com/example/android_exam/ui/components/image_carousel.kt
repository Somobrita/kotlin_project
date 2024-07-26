package com.example.android_exam.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import kotlin.math.abs
import kotlinx.coroutines.launch

@Composable
fun ImageCarousel(
    images: List<Painter>,
    scrollState: LazyListState,
    onIndexChanged: (Int) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val coroutineScope = rememberCoroutineScope()

    var currentIndex by remember { mutableStateOf(0) }

    // Update current index based on scroll state
    LaunchedEffect(scrollState.layoutInfo) {
        val layoutInfo = scrollState.layoutInfo
        val visibleItemsInfo = layoutInfo.visibleItemsInfo
        if (visibleItemsInfo.isNotEmpty()) {
            val viewportWidth = layoutInfo.viewportSize.width
            val centeredItem = visibleItemsInfo.minByOrNull { item ->
                val itemCenter = item.offset + item.size / 2
                val viewportCenter = viewportWidth / 2
                abs(itemCenter - viewportCenter)
            }
            val newIndex = centeredItem?.index ?: 0
            if (newIndex != currentIndex) {
                currentIndex = newIndex
                onIndexChanged(newIndex)
            }
        }
    }

    LaunchedEffect(currentIndex) {
        coroutineScope.launch {
            scrollState.animateScrollToItem(currentIndex, 0)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .border(2.dp, Color.White, RoundedCornerShape(12.dp))
        ) {
            LazyRow(
                state = scrollState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalArrangement = Arrangement.Center,
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(images) { image ->
                    Box(
                        modifier = Modifier
                            .width(screenWidth) // Ensure each item occupies full screen width
                            .padding(4.dp)
                    ) {
                        Image(
                            painter = image,
                            contentDescription = "Carousel Image",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        // Indicator dots
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            images.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = if (index == currentIndex) Color(0xFF03DAC5) else Color.Gray,
                            shape = CircleShape
                        )
                )
                if (index != images.lastIndex) {
                    Spacer(modifier = Modifier.width(8.dp)) // Add space between dots
                }
            }
        }
    }
}
