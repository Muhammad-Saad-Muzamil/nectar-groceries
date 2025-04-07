

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nectar.R
import com.example.nectar.ui.favorites.FavoriteItem

class FavoriteItemAdapter(
    private val favoriteItems: List<FavoriteItem>,
    private val onItemClicked: (FavoriteItem) -> Unit
) : RecyclerView.Adapter<FavoriteItemAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val itemName: TextView = view.findViewById(R.id.itemName)
        val itemDescription: TextView = view.findViewById(R.id.itemDescription)
        val itemPrice: TextView = view.findViewById(R.id.itemPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favouites, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = favoriteItems[position]
        holder.itemName.text = item.name
        holder.itemImage.setImageResource(item.imageRes)
        holder.itemDescription.text = item.description
        holder.itemPrice.text = String.format("$%.2f", item.price)
        holder.itemView.setOnClickListener { onItemClicked(item) }
    }

    override fun getItemCount(): Int = favoriteItems.size
}
