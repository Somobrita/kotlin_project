@file:OptIn(ExperimentalMaterialApi::class)

package com.example.android_exam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val images = listOf(
        painterResource(id = R.drawable.image1),
        painterResource(id = R.drawable.image2),
        painterResource(id = R.drawable.image3)
    )

    // Separate lists for each image
    val list1 = listOf("Apple", "Banana", "Orange", "Blueberry")
    val list2 = listOf("Avocado", "Pineapple", "Grapefruit", "Strawberry")
    val list3 = listOf("Peach", "Plum", "Mango", "Kiwi")
    val lists = listOf(list1, list2, list3)

    var currentIndex by remember { mutableStateOf(0) }
    val currentList by remember { derivedStateOf { lists[currentIndex] } }
    val filteredItems by remember { derivedStateOf { currentList.filter { it.contains(searchText.text, ignoreCase = true) } } }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            StatsBottomSheet(items = filteredItems)
        },
        sheetPeekHeight = 0.dp,
        backgroundColor = Color.Transparent
    ) { padding ->
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    },
                    backgroundColor = Color(0xFF03DAC5)
                ) {
                    Text("+", color = Color.White, fontSize = 24.sp)
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding) // Apply innerPadding to manage layout
                    .fillMaxSize()
            ) {
                // Image carousel
                val imageScrollState = rememberLazyListState()

                LaunchedEffect(imageScrollState.firstVisibleItemIndex) {
                    currentIndex = imageScrollState.firstVisibleItemIndex
                }

                ImageCarousel(
                    images = images,
                    scrollState = imageScrollState
                )

                // Search bar
                val listState = rememberLazyListState()
                var searchBarTop by remember { mutableStateOf(0.dp) }

                LaunchedEffect(listState.firstVisibleItemIndex) {
                    val offset = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.offset ?: 0
                    searchBarTop = if (offset < 0) 0.dp else offset.dp
                }

                SearchBar(
                    searchText = searchText,
                    onSearchTextChange = { searchText = it },
                    searchBarTop = searchBarTop
                )

                // List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    state = listState
                ) {
                    items(filteredItems) { item ->
                        ItemCard(item = item)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    searchText: TextFieldValue,
    onSearchTextChange: (TextFieldValue) -> Unit,
    searchBarTop: Dp
) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .offset(y = searchBarTop)
            .background(Color.White, shape = RoundedCornerShape(40.dp)) // Rounded corners for a modern look
            .border(1.dp, Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(40.dp)) // Subtle border color
            .padding(horizontal = 8.dp) // Adjusted padding
    ) {
        TextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            placeholder = {
                Text(
                    text = "Search",
                    color = Color.Gray, // Placeholder text color
                    style = MaterialTheme.typography.body2.copy(fontSize = 14.sp) // Ensure placeholder text is visible
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Gray
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent, // Transparent background
                focusedIndicatorColor = Color.Transparent, // Remove the indicator underline
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.body2.copy(fontSize = 16.sp)
        )
    }
}

@Composable
fun ImageCarousel(images: List<Painter>, scrollState: LazyListState) {
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(scrollState.layoutInfo) {
        val layoutInfo = scrollState.layoutInfo
        val visibleItemsInfo = layoutInfo.visibleItemsInfo
        if (visibleItemsInfo.isNotEmpty()) {
            val viewportWidth = layoutInfo.viewportSize.width
            val centeredItem = visibleItemsInfo.minByOrNull { item ->
                val itemCenter = item.offset + item.size / 2
                val viewportCenter = viewportWidth / 2
                kotlin.math.abs(itemCenter - viewportCenter)
            }
            currentIndex = centeredItem?.index ?: 0
        }
    }

    Box(
        modifier = Modifier
            .height(200.dp)
            .padding(8.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .border(2.dp, Color.White, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            state = scrollState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            horizontalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(images.size) { index ->
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(4.dp)
                        .background(Color.Gray, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Image(
                        painter = images[index],
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
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(images.size) { dotIndex ->
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        color = if (dotIndex == currentIndex) Color.Black else Color.Gray,
                        shape = CircleShape
                    )
                    .padding(2.dp)
            )
        }
    }
}

@Composable
fun ItemCard(item: String) {
    Card(
        backgroundColor = Color.White,
        elevation = 8.dp,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = item,
                style = MaterialTheme.typography.h6.copy(fontSize = 18.sp),
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun StatsBottomSheet(items: List<String>) {
    val charCount = items.joinToString("").groupingBy { it }.eachCount()
    val sortedChars = charCount.entries.sortedByDescending { it.value }.take(3)
    val totalCount = items.size

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp)) // Softer corners
            .border(1.dp, Color.Gray.copy(alpha = 0.3f), shape = RoundedCornerShape(16.dp))
            .padding(16.dp) // Added inner padding
    ) {
        Text(
            text = "Statistics",
            style = MaterialTheme.typography.h5.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp) // Added bottom padding
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
