package com.example.android_exam.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.ui.text.font.FontWeight

@Composable
fun StatsBottomSheet(items: List<String>) {
    // Exclude spaces and calculate character counts
    val charCount = items.joinToString("").filter { it.isLetterOrDigit() }.groupingBy { it }.eachCount()
    val sortedChars = charCount.entries.sortedByDescending { it.value }.take(3)
    val totalCount = items.size

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .border(1.dp, Color.Gray.copy(alpha = 0.3f), shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Statistics",
            style = MaterialTheme.typography.h5.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Total Items: $totalCount",
            style = MaterialTheme.typography.body1.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Top 3 Characters:",
            style = MaterialTheme.typography.body1.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            color = Color.Black
        )
        sortedChars.forEach { (char, count) ->
            Text(
                text = "$char: $count",
                style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                color = Color.Black
            )
        }

    }
}
