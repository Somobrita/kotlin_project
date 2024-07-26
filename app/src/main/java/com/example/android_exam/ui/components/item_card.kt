package com.example.android_exam.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip

@Composable
fun ItemCard(
    item: Pair<String, String>,
    image: Painter
) {
    val (name, subtitle) = item

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Slightly reduced elevation for a more subtle shadow
        shape = RoundedCornerShape(12.dp), // Less rounded corners for a sleeker look
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp) // Balanced padding around the card
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Image
            Image(
                painter = image,
                contentDescription = "Item Image",
                modifier = Modifier
                    .size(80.dp) // Square size for the image
                    .clip(RoundedCornerShape(12.dp)) // Rounded corners for the square image
            )

            Spacer(modifier = Modifier.width(8.dp))
            // Text Column
            Column(
                modifier = Modifier
                    .weight(1f) // Ensures the text column fills available space
                    .padding(vertical = 4.dp) // Vertical padding to center text
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp), // Consistent text style
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp)) // Space between title and subtitle
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp), // Slightly larger subtitle text
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
