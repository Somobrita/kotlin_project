package com.example.android_exam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.widget.SearchView
import android.widget.ImageView
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// Data class for List items
data class ListItem(val imageResId: Int, val title: String, val subtitle: String)

// Adapter for RecyclerView
class ItemAdapter(var items: List<ListItem>) : Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemImageView)
        val title: TextView = itemView.findViewById(R.id.itemTitle)
        val subtitle: TextView = itemView.findViewById(R.id.itemSubtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.imageView.setImageResource(item.imageResId)
        holder.title.text = item.title
        holder.subtitle.text = item.subtitle
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<ListItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}

// Adapter for ViewPager2
class ImageCarouselAdapter(val images: List<Int>) : Adapter<ImageCarouselAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.carouselImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carousel_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(images[position])
    }

    override fun getItemCount(): Int = images.size
}

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var imageCarouselAdapter: ImageCarouselAdapter

    // Define your lists of items
    val list1 = listOf(
        ListItem(R.drawable.image1, "Mount Fuji", "Japan's iconic mountain and a popular hiking destination"),
        ListItem(R.drawable.image1, "Machu Picchu", "Historic Incan city located in the Andes mountains of Peru"),
        ListItem(R.drawable.image1, "Swiss Alps", "Scenic mountain range offering skiing and hiking opportunities"),
        ListItem(R.drawable.image1, "Black Forest", "Wooded mountain range in southwest Germany, known for its picturesque landscapes"),
        ListItem(R.drawable.image1, "Kashmir Valley", "Beautiful valley in the Indian Himalayas, known for its scenic beauty"),
        ListItem(R.drawable.image1, "Munnar", "Hill station in Kerala, India, famous for its tea plantations"),
        ListItem(R.drawable.image1, "Shimla", "Capital city of Himachal Pradesh, India, known for its colonial architecture and cool climate"),
        ListItem(R.drawable.image1, "Darjeeling", "Hill station in West Bengal, India, known for its tea gardens and views of Kanchenjunga"),
        // Additional items
        ListItem(R.drawable.image1, "Rocky Mountains", "A major mountain range in western North America known for its stunning landscapes"),
        ListItem(R.drawable.image1, "Himalayas", "The tallest mountain range in the world, spanning five countries in Asia"),
        ListItem(R.drawable.image1, "Andes Mountains", "The longest continental mountain range in the world, located in South America"),
        ListItem(R.drawable.image1, "Appalachian Mountains", "A mountain range in eastern North America known for its rich biodiversity"),
        ListItem(R.drawable.image1, "Pyrenees", "A mountain range separating the Iberian Peninsula from the rest of Europe"),
        ListItem(R.drawable.image1, "Carpathian Mountains", "A mountain range in Central and Eastern Europe, known for its rich wildlife")
    )

    val list2 = listOf(
        ListItem(R.drawable.image2, "Zermatt", "A Swiss village at the base of the Matterhorn, popular for skiing and mountaineering"),
        ListItem(R.drawable.image2, "Lapland", "Region in Finland known for its winter activities and Northern Lights"),
        ListItem(R.drawable.image2, "Siberia", "Vast region in Russia known for its extreme cold and snow"),
        ListItem(R.drawable.image2, "Iceland", "Known for its glaciers and ice caves"),
        ListItem(R.drawable.image2, "Gulmarg", "Popular skiing destination in Jammu and Kashmir, India"),
        ListItem(R.drawable.image2, "Auli", "Snow-capped skiing resort in Uttarakhand, India"),
        ListItem(R.drawable.image2, "Manali", "Hill station in Himachal Pradesh, India, known for its winter sports"),
        ListItem(R.drawable.image2, "Nathula Pass", "Mountain pass in Sikkim, India, known for its snowy conditions"),
        // Additional items
        ListItem(R.drawable.image2, "Banff", "A Canadian town in Alberta known for its stunning mountainous surroundings"),
        ListItem(R.drawable.image2, "Aspen", "A ski resort town in Colorado, USA, known for its winter sports"),
        ListItem(R.drawable.image2, "Nagano", "A city in Japan known for hosting the 1998 Winter Olympics"),
        ListItem(R.drawable.image2, "Chamonix", "A town in the French Alps known for its skiing and mountaineering"),
        ListItem(R.drawable.image2, "Whistler", "A Canadian resort town known for its world-class ski slopes")
    )

    val list3 = listOf(
        ListItem(R.drawable.image3, "Lake Bled", "A picturesque lake with an island and castle in Slovenia"),
        ListItem(R.drawable.image3, "Lake Como", "An Italian lake surrounded by mountains and charming villages"),
        ListItem(R.drawable.image3, "Loch Ness", "A Scottish lake known for its mysterious legend of the Loch Ness Monster"),
        ListItem(R.drawable.image3, "Lake Atitlán", "A volcanic crater lake in the Guatemalan Highlands"),
        ListItem(R.drawable.image3, "Dal Lake", "A serene lake in Srinagar, Jammu and Kashmir, India"),
        ListItem(R.drawable.image3, "Naini Lake", "A picturesque lake in Nainital, Uttarakhand, India"),
        ListItem(R.drawable.image3, "Pangong Lake", "A high-altitude lake in Ladakh, India"),
        ListItem(R.drawable.image3, "Sattal", "A group of interconnected lakes in Uttarakhand, India"),
        // Additional items
        ListItem(R.drawable.image3, "Lake Tahoe", "A large freshwater lake in the Sierra Nevada Mountains, USA"),
        ListItem(R.drawable.image3, "Crater Lake", "A deep blue lake in Oregon, USA, formed by a collapsed volcano"),
        ListItem(R.drawable.image3, "Great Lakes", "A group of five large freshwater lakes in North America"),
        ListItem(R.drawable.image3, "Lake Victoria", "The largest lake in Africa, shared by Uganda, Kenya, and Tanzania"),
        ListItem(R.drawable.image3, "Baikal Lake", "A rift lake in Siberia, Russia, the world's deepest and oldest freshwater lake")
    )

    val list4 = listOf(
        ListItem(R.drawable.image4, "Sahara Desert", "The largest hot desert in the world, located in North Africa"),
        ListItem(R.drawable.image4, "Death Valley", "A desert valley in eastern California, USA"),
        ListItem(R.drawable.image4, "Namib Desert", "A coastal desert in southern Africa"),
        ListItem(R.drawable.image4, "Arabian Desert", "A vast desert wilderness in Western Asia"),
        ListItem(R.drawable.image4, "Kalahari Desert", "A semi-arid sandy savanna in southern Africa"),
        ListItem(R.drawable.image4, "Rann of Kutch", "A salt marsh in Gujarat, India, known for its white salt desert"),
        ListItem(R.drawable.image4, "Ladakh", "A high-altitude desert region in northern India"),
        ListItem(R.drawable.image4, "Jodhpur", "City in Rajasthan, India, known for its proximity to desert landscapes"),
        // Additional items
        ListItem(R.drawable.image4, "Gobi Desert", "A vast desert region in northern China and southern Mongolia"),
        ListItem(R.drawable.image4, "Atacama Desert", "The driest non-polar desert in the world, located in Chile"),
        ListItem(R.drawable.image4, "Thar Desert", "A large arid region in northwestern India and eastern Pakistan"),
        ListItem(R.drawable.image4, "Simpson Desert", "A large desert in central Australia known for its red sand dunes")
    )

    // Map images to corresponding lists
    val imageItemMap: Map<Int, List<ListItem>> = mapOf(
        R.drawable.image1 to list1,
        R.drawable.image2 to list2,
        R.drawable.image3 to list3,
        R.drawable.image4 to list4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        recyclerView = findViewById(R.id.recyclerView)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val searchView = findViewById<SearchView>(R.id.search_view)
        val tabLayout = findViewById<TabLayout>(R.id.tabDots)

        // Initialize adapters
        imageCarouselAdapter = ImageCarouselAdapter(imageItemMap.keys.toList())
        viewPager.adapter = imageCarouselAdapter

        itemAdapter = ItemAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = itemAdapter

        // Load initial list content for the first image
        if (imageCarouselAdapter.itemCount > 0) {
            updateListContentForPage(imageCarouselAdapter.images[0])
        }

        // Set up page change listener
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val selectedImage = imageCarouselAdapter.images[position]
                updateListContentForPage(selectedImage)
            }
        })

        // Set up TabLayout with dots
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            // Tabs are automatically created, no need to add any specific functionality here
        }.attach()

        // Set custom drawable for TabLayout dots
        tabLayout.apply {
            tabMode = TabLayout.MODE_FIXED
            setSelectedTabIndicatorHeight(0) // Hide the default indicator
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.view?.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.tab_dot_selected)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    tab?.view?.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.tab_dot)
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    // Optionally handle reselection
                }
            })
        }

        // Set up search functionality
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText.orEmpty())
                return true
            }
        })

        // Set up Floating Action Button
        fab.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun updateListContentForPage(imageResId: Int) {
        val items = imageItemMap[imageResId] ?: emptyList()
        itemAdapter.updateItems(items)
    }

    private fun filterList(query: String) {
        val currentItems = itemAdapter.items
        val filteredItems = currentItems.filter {
            it.title.contains(query, ignoreCase = true) || it.subtitle.contains(query, ignoreCase = true)
        }
        itemAdapter.updateItems(filteredItems)
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
        val itemCountTextView = view.findViewById<TextView>(R.id.itemCountTextView)
        val topCharactersTextView = view.findViewById<TextView>(R.id.topCharactersTextView)

        // Calculate item counts and top characters
        val itemCount = itemAdapter.items.size
        val characterCountMap = mutableMapOf<Char, Int>()

        itemAdapter.items.forEach { item ->
            item.title.forEach { char ->
                if (char.isLetter()) {
                    characterCountMap[char] = characterCountMap.getOrDefault(char, 0) + 1
                }
            }
        }

        val topCharacters = characterCountMap.entries
            .sortedByDescending { it.value }
            .take(3)
            .joinToString(", ") { "${it.key} (${it.value})" }

        itemCountTextView.text = "Item Count: $itemCount"
        topCharactersTextView.text = "Top Characters: $topCharacters"

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }
}
