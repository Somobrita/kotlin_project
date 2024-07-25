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
        painterResource(id = R.drawable.image3),
        painterResource(id = R.drawable.image4),
        painterResource(id = R.drawable.image5)
        )

    // Lists for each image
    val list1 = listOf(
        "Mount Fuji" to "Japan's iconic mountain and a popular hiking destination",
        "Machu Picchu" to "Historic Incan city located in the Andes mountains of Peru",
        "Swiss Alps" to "Scenic mountain range offering skiing and hiking opportunities",
        "Great Smoky Mountains" to "Mountain range straddling the border of North Carolina and Tennessee, USA",
        "Himalayas" to "Home to Mount Everest and a major trekking destination in Asia",
        "Andes Mountains" to "Longest continental mountain range in the world, running through South America",
        "Blue Mountains" to "Mountain range in New South Wales, Australia, known for its dramatic scenery",
        "Rocky Mountains" to "Major mountain range in western North America, offering various outdoor activities",
        "Black Forest" to "Wooded mountain range in southwest Germany, known for its picturesque landscapes",
        "Scottish Highlands" to "Mountainous region in Scotland, famous for its stunning views and history",
        "Kashmir Valley" to "Beautiful valley in the Indian Himalayas, known for its scenic beauty",
        "Munnar" to "Hill station in Kerala, India, famous for its tea plantations",
        "Shimla" to "Capital city of Himachal Pradesh, India, known for its colonial architecture and cool climate",
        "Darjeeling" to "Hill station in West Bengal, India, known for its tea gardens and views of Kanchenjunga"
    )


    val list2 = listOf(
        "Banff National Park" to "Located in the Canadian Rockies, known for its stunning winter landscapes",
        "Zermatt" to "A Swiss village at the base of the Matterhorn, popular for skiing and mountaineering",
        "Aspen" to "A ski resort town in Colorado, USA, known for its winter sports",
        "Chamonix" to "A ski resort in the French Alps, famous for its challenging slopes",
        "Lapland" to "Region in Finland known for its winter activities and Northern Lights",
        "Hokkaido" to "Japanese island renowned for its powder snow and ski resorts",
        "Whistler" to "A Canadian resort town with a world-renowned ski area",
        "Siberia" to "Vast region in Russia known for its extreme cold and snow",
        "Norwegian Fjords" to "Fjords in Norway offering stunning snowy landscapes",
        "Iceland" to "Known for its glaciers and ice caves",
        "Gulmarg" to "Popular skiing destination in Jammu and Kashmir, India",
        "Auli" to "Snow-capped skiing resort in Uttarakhand, India",
        "Manali" to "Hill station in Himachal Pradesh, India, known for its winter sports",
        "Nathula Pass" to "Mountain pass in Sikkim, India, known for its snowy conditions"
    )

    val list3 = listOf(
        "Lake Bled" to "A picturesque lake with an island and castle in Slovenia",
        "Lake Como" to "An Italian lake surrounded by mountains and charming villages",
        "Plitvice Lakes" to "A national park in Croatia with terraced lakes and waterfalls",
        "Lake Tahoe" to "A large freshwater lake in the Sierra Nevada Mountains, USA",
        "Loch Ness" to "A Scottish lake known for its mysterious legend of the Loch Ness Monster",
        "Lake Louise" to "A glacial lake in Banff National Park, Canada",
        "Lake District" to "A mountainous region in England with beautiful lakes and scenery",
        "Lake Titicaca" to "The highest navigable lake in the world, located in the Andes",
        "Lugu Lake" to "A highland lake in the Yunnan-Guizhou Plateau, China",
        "Lake Atitlán" to "A volcanic crater lake in the Guatemalan Highlands",
        "Dal Lake" to "A serene lake in Srinagar, Jammu and Kashmir, India",
        "Naini Lake" to "A picturesque lake in Nainital, Uttarakhand, India",
        "Pangong Lake" to "A high-altitude lake in Ladakh, India",
        "Sattal" to "A group of interconnected lakes in Uttarakhand, India"
    )

    val list4 = listOf(
        "Sahara Desert" to "The largest hot desert in the world, located in North Africa",
        "Wadi Rum" to "A protected desert wilderness in southern Jordan",
        "Atacama Desert" to "A desert plateau in South America, known as the driest place on Earth",
        "Death Valley" to "A desert valley in eastern California, USA",
        "Namib Desert" to "A coastal desert in southern Africa",
        "Gobi Desert" to "A vast desert region in northern China and southern Mongolia",
        "Thar Desert" to "A large arid region in northwestern India and eastern Pakistan",
        "Arabian Desert" to "A vast desert wilderness in Western Asia",
        "Kalahari Desert" to "A semi-arid sandy savanna in southern Africa",
        "Mojave Desert" to "A desert in the southwestern United States",
        "Rann of Kutch" to "A salt marsh in Gujarat, India, known for its white salt desert",
        "Jaisalmer Desert" to "Part of the Thar Desert in Rajasthan, India, known for its golden sand dunes",
        "Ladakh" to "A high-altitude desert region in northern India",
        "Jodhpur" to "City in Rajasthan, India, known for its proximity to desert landscapes"
    )
    val list5 = listOf(
        "Maui" to "An island in Hawaii known for its beautiful beaches and lush landscapes",
        "Bora Bora" to "A small South Pacific island known for its turquoise waters and coral reefs",
        "Maldives" to "An archipelago in the Indian Ocean known for its white sand beaches",
        "Bali" to "An Indonesian island famous for its beaches and vibrant culture",
        "Bondi Beach" to "A popular beach in Sydney, Australia",
        "Seychelles" to "An archipelago of 115 islands in the Indian Ocean",
        "Santorini" to "A Greek island known for its stunning beaches and whitewashed buildings",
        "Cancún" to "A Mexican city with a famous beach resort area",
        "Phuket" to "Thailand's largest island, known for its beaches and nightlife",
        "Copacabana" to "A famous beach in Rio de Janeiro, Brazil",
        "Goa" to "Indian state known for its beautiful beaches and vibrant nightlife",
        "Kovalam" to "A beach town in Kerala, India, known for its serene beaches",
        "Pondicherry" to "Coastal town in India with charming beaches and French colonial architecture",
        "Varkala" to "A beach destination in Kerala, India, known for its cliffs and scenic beauty"
    )


    val lists = listOf(list1, list2, list3,list4,list5)

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
        listScrollState.animateScrollToItem(0) // Reset the scroll to the top of the list
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

                // Vertically scrollable list
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    state = listScrollState
                ) {
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
