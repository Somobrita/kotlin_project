// ItemAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_exam.R

data class ListItem(val imageResId: Int, val title: String, val subtitle: String)

class ItemAdapter(private var items: List<ListItem>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
