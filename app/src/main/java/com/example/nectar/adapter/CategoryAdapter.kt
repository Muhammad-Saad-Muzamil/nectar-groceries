import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.nectar.R
import com.example.nectar.model.Category

class CategoryAdapter(
    private val context: Context,
    private val categoryList: List<Category>,
    private val onItemClick: (Category) -> Unit // Add this lambda for item clicks
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.categoryName.text = category.name
        holder.categoryImage.setImageResource(category.imageResource)

        // Set background color
        holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, category.bgColor))

        // Set click listener
        holder.cardView.setOnClickListener {
            onItemClick(category) // Trigger the lambda when the card is clicked
        }
    }

    override fun getItemCount(): Int = categoryList.size

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        val categoryImage: ImageView = itemView.findViewById(R.id.categoryImage)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }
}