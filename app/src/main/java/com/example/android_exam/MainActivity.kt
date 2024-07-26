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
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

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
class ImageCarouselAdapter(private val images: List<Int>) : Adapter<ImageCarouselAdapter.ImageViewHolder>() {

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
    private val initialItems = listOf(
        ListItem(R.drawable.image1, "Title 1", "Subtitle 1"),
        ListItem(R.drawable.image2, "Title 2", "Subtitle 2"),
        ListItem(R.drawable.image3, "Title 3", "Subtitle 3")
        // Add more items as needed
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        recyclerView = findViewById(R.id.recyclerView)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val searchView = findViewById<SearchView>(R.id.search_view)

        // Initialize adapters
        imageCarouselAdapter = ImageCarouselAdapter(listOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3
            // Add more images as needed
        ))
        viewPager.adapter = imageCarouselAdapter

        itemAdapter = ItemAdapter(initialItems)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = itemAdapter

        // Set up page change listener
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateListContentForPage(position)
            }
        })

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

    private fun updateListContentForPage(position: Int) {
        // Update the list content based on the selected page
        val updatedItems = initialItems // Replace with actual logic based on the page position
        itemAdapter.updateItems(updatedItems)
    }

    private fun filterList(query: String) {
        val filteredItems = initialItems.filter {
            it.title.contains(query, ignoreCase = true)
        }
        itemAdapter.updateItems(filteredItems)
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
        val itemCountTextView = view.findViewById<TextView>(R.id.itemCountTextView)
        val topCharactersTextView = view.findViewById<TextView>(R.id.topCharactersTextView)

        val currentItems = itemAdapter.items
        itemCountTextView.text = "Items: ${currentItems.size}"

        val charFrequency = currentItems.flatMap { it.title.toList() }
            .groupingBy { it }
            .eachCount()
            .entries
            .sortedByDescending { it.value }
            .take(3)

        topCharactersTextView.text = charFrequency.joinToString("\n") {
            "${it.key} = ${it.value}"
        }

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }
}
