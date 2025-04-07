import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nectar.R
import com.example.nectar.adapter.ProductAdapter
import com.example.nectar.model.ProductViewModel

class HomeFragment : Fragment() {

    private lateinit var recyclerViewExclusive: RecyclerView
    private lateinit var recyclerViewBestSelling: RecyclerView
    private lateinit var recyclerViewGroceries: RecyclerView
    private lateinit var exclusiveAdapter: ProductAdapter
    private lateinit var bestSellingAdapter: ProductAdapter
    private lateinit var groceryAdapter: ProductAdapter
    private lateinit var searchInput: EditText
    private val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerViews
        recyclerViewExclusive = view.findViewById(R.id.recyclerViewExclusive)
        recyclerViewBestSelling = view.findViewById(R.id.recyclerViewBestSelling)
        recyclerViewGroceries = view.findViewById(R.id.recyclerViewGroceries)
        searchInput = view.findViewById(R.id.searchBar)

        // Set up LayoutManagers for each RecyclerView
        recyclerViewExclusive.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewBestSelling.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewGroceries.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Initialize Adapters
        exclusiveAdapter = ProductAdapter(ArrayList())
        bestSellingAdapter = ProductAdapter(ArrayList())
        groceryAdapter = ProductAdapter(ArrayList())

        // Set adapters to RecyclerViews
        recyclerViewExclusive.adapter = exclusiveAdapter
        recyclerViewBestSelling.adapter = bestSellingAdapter
        recyclerViewGroceries.adapter = groceryAdapter

        // Observe Product List from ViewModel
        productViewModel.productList.observe(viewLifecycleOwner) { products ->
            val exclusiveProducts = products.filter { it.category == "Exclusive" }
            val bestSellingProducts = products.filter { it.category == "Best Selling" }
            val groceryProducts = products.filter { it.category == "Groceries" }

            // Update each adapter with the filtered data
            exclusiveAdapter.updateList(exclusiveProducts)
            bestSellingAdapter.updateList(bestSellingProducts)
            groceryAdapter.updateList(groceryProducts)
        }

        // Search Logic
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                productViewModel.searchProducts(s.toString()) // Filter products based on search
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
