package com.example.android_exam.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.android_exam.R
import com.example.android_exam.ui.components.ImageCarousel
import com.example.android_exam.ui.components.ItemCard
import com.example.android_exam.ui.components.ListsProvider
import com.example.android_exam.ui.components.SearchBar
import com.example.android_exam.ui.components.StatsBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomePage() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    // Local images
    val images = listOf(
        painterResource(id = R.drawable.image1),
        painterResource(id = R.drawable.image2),
        painterResource(id = R.drawable.image3),
        painterResource(id = R.drawable.image4),
        painterResource(id = R.drawable.image5)
    )

    // Use lists from ListsProvider
    val lists = ListsProvider.allLists

    var currentIndex by remember { mutableStateOf(0) }
    val currentList by remember { derivedStateOf { lists[currentIndex] } }
    val filteredItems by remember { derivedStateOf { currentList.filter { it.first.contains(searchText.text, ignoreCase = true) } } }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    // Scroll state for the entire page
    val scrollState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset: Offset ->
                    if (isBottomSheetVisible) {
                        // Hide the bottom sheet on touch outside
                        scope.launch {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                        isBottomSheetVisible = false
                    }
                }
            }
    ) {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                StatsBottomSheet(items = filteredItems.map { it.first }) // Assuming StatsBottomSheet takes a list of item names
            },
            sheetPeekHeight = 5.dp,
            backgroundColor = Color.Transparent
        ) {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                            isBottomSheetVisible = true
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
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    state = scrollState
                ) {
                    // Image carousel
                    item {
                        ImageCarousel(
                            images = images,
                            scrollState = rememberLazyListState() // Use an independent state for the carousel
                        ) { newIndex ->
                            currentIndex = newIndex
                        }
                    }

                    // Sticky Search Bar
                    stickyHeader {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White) // Optional: background for clarity
                                .padding(8.dp)
                        ) {
                            SearchBar(
                                searchText = searchText,
                                onSearchTextChange = { searchText = it },
                                searchBarTop = 0.dp
                            )
                        }
                    }

                    // List items
                    items(filteredItems) { item ->
                        // Use the currentIndex to determine which image to use for all items in the list
                        val itemImage = images[currentIndex]
                        ItemCard(item = item, image = itemImage)
                    }
                }
            }
        }
    }
}
