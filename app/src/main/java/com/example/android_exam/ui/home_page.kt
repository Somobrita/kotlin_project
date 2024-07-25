package com.example.android_exam.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.android_exam.R
import com.example.android_exam.ui.components.ImageCarousel
import com.example.android_exam.ui.components.ItemCard
import com.example.android_exam.ui.components.SearchBar
import com.example.android_exam.ui.components.StatsBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePage() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    // Local images
    val images = listOf(
        painterResource(id = R.drawable.image1),
        painterResource(id = R.drawable.image2),
        painterResource(id = R.drawable.image3)
    )

    // Lists for each image
    val list1 = listOf(
        "Apple" to "A juicy fruit",
        "Banana" to "A yellow fruit",
        "Orange" to "A citrus fruit",
        "Blueberry" to "A small berry"
    )
    val list2 = listOf(
        "Avocado" to "A creamy fruit",
        "Pineapple" to "A tropical fruit",
        "Grapefruit" to "A tangy fruit",
        "Strawberry" to "A sweet berry"
    )
    val list3 = listOf(
        "Peach" to "A sweet fruit",
        "Plum" to "A tart fruit",
        "Mango" to "A tropical fruit",
        "Kiwi" to "A tangy berry"
    )
    val lists = listOf(list1, list2, list3)

    var currentIndex by remember { mutableStateOf(0) }
    val currentList by remember { derivedStateOf { lists[currentIndex] } }
    val filteredItems by remember { derivedStateOf { currentList.filter { it.first.contains(searchText.text, ignoreCase = true) } } }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    // Scroll state for carousel and list
    val imageScrollState = rememberLazyListState()
    val listScrollState = rememberLazyListState()

    // Synchronize list based on carousel index
    LaunchedEffect(currentIndex) {
        listScrollState.animateScrollToItem(currentIndex)
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            StatsBottomSheet(items = filteredItems.map { it.first }) // Assuming StatsBottomSheet takes a list of item names
        },
        sheetPeekHeight = 5.dp,
        backgroundColor = Color.Transparent
    ) { innerPadding ->
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
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "More Options",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                // Image carousel
                ImageCarousel(
                    images = images,
                    scrollState = imageScrollState
                ) { newIndex -> currentIndex = newIndex }

                // Search bar
                var searchBarTop by remember { mutableStateOf(0.dp) }

                LaunchedEffect(listScrollState.firstVisibleItemIndex) {
                    val offset = listScrollState.layoutInfo.visibleItemsInfo.firstOrNull()?.offset ?: 0
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
                    state = listScrollState
                ) {
                    items(filteredItems) { item ->
                        ItemCard(item = item)
                    }
                }
            }
        }
    }
}
