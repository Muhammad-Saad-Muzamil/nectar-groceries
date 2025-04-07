import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nectar.R
import com.example.nectar.model.Category

class ExploreFragment : Fragment() {

    private lateinit var categoriesGrid: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        categoriesGrid = view.findViewById(R.id.categoriesGrid)
        categoriesGrid.layoutManager = GridLayoutManager(requireContext(), 2)

        // Create a list of categories
        val categoryList = listOf(
            Category("1", "Fresh Fruits & Vegetable", R.color.colorFruits, R.drawable.fruits_vegetables),
            Category("2", "Cooking Oil & Ghee", R.color.colorOil, R.drawable.cooking_oil),
            Category("3", "Meat & Fish", R.color.colorDairy, R.drawable.meat_fish),
            Category("4", "Bakery & Snacks", R.color.colorBakery, R.drawable.bakery_snacks),
            Category("5", "Dairy & Eggs", R.color.colorMeat, R.drawable.dairy_eggs),
            Category("6", "Beverages", R.color.colorBeverages, R.drawable.beverges)
        )

        // Initialize and set the adapter
        categoryAdapter = CategoryAdapter(requireContext(), categoryList) { category ->
            when (category.name) {
                "Beverages" -> {
                    // Navigate to BeveragesDetailFragment
                    val fragment = BeveragesDetailFragment()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                else -> {
                    // Navigate to the default CategoryDetailFragment for other categories
                    val fragment = CategoryDetailFragment.newInstance(category)
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }

        // Set the adapter to the RecyclerView
        categoriesGrid.adapter = categoryAdapter
    }
}