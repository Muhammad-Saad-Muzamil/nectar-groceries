//    package com.example.nectar.ui.home
//
//    import android.os.Bundle
//    import android.text.Editable
//    import android.text.TextWatcher
//    import android.view.LayoutInflater
//    import android.view.View
//    import android.view.ViewGroup
//    import android.widget.EditText
//    import androidx.fragment.app.Fragment
//    import androidx.fragment.app.activityViewModels
//    import androidx.recyclerview.widget.LinearLayoutManager
//    import androidx.recyclerview.widget.RecyclerView
//    import com.example.nectar.R
//    import com.example.nectar.adapter.ProductAdapter
//    import com.example.nectar.viewmodel.ProductViewModel
//
//    class HomeFragment : Fragment() {
//
//        private lateinit var recyclerViewExclusive: RecyclerView
//        private lateinit var recyclerViewBestSelling: RecyclerView
//        private lateinit var recyclerViewGroceries: RecyclerView
//        private lateinit var exclusiveAdapter: ProductAdapter
//        private lateinit var bestSellingAdapter: ProductAdapter
//        private lateinit var groceryAdapter: ProductAdapter
//        private lateinit var searchInput: EditText
//
//        private val productViewModel: ProductViewModel by activityViewModels()
//
//        override fun onCreateView(
//            inflater: LayoutInflater, container: ViewGroup?,
//            savedInstanceState: Bundle?
//        ): View {
//            return inflater.inflate(R.layout.fragment_home, container, false)
//        }
//
//        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//            super.onViewCreated(view, savedInstanceState)
//
//            // Find views
//            recyclerViewExclusive = view.findViewById(R.id.recyclerViewExclusive)
//            recyclerViewBestSelling = view.findViewById(R.id.recyclerViewBestSelling)
//            recyclerViewGroceries = view.findViewById(R.id.recyclerViewGroceries)
//            searchInput = view.findViewById(R.id.searchBar)
//
//            // Set layout managers
//            recyclerViewExclusive.layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            recyclerViewBestSelling.layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            recyclerViewGroceries.layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//
//            // Initialize adapters
//            exclusiveAdapter = ProductAdapter(emptyList())
//            bestSellingAdapter = ProductAdapter(emptyList())
//            groceryAdapter = ProductAdapter(emptyList())
//
//            // Attach adapters
//            recyclerViewExclusive.adapter = exclusiveAdapter
//            recyclerViewBestSelling.adapter = bestSellingAdapter
//            recyclerViewGroceries.adapter = groceryAdapter
//
//            // Observe full product list (used for filtering by category)
//            productViewModel.filteredList.observe(viewLifecycleOwner) { products ->
//                val exclusiveProducts = products.filter { it.category.equals("Exclusive", true) }
//                val bestSellingProducts = products.filter { it.category.equals("Best Selling", true) }
//                val groceryProducts = products.filter { it.category.equals("Groceries", true) }
//
//                exclusiveAdapter.updateList(exclusiveProducts)
//                bestSellingAdapter.updateList(bestSellingProducts)
//                groceryAdapter.updateList(groceryProducts)
//            }
//
//            // Search filter
//            searchInput.addTextChangedListener(object : TextWatcher {
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    productViewModel.searchProducts(s.toString())
//                }
//                override fun afterTextChanged(s: Editable?) {}
//            })
//        }
//    }

package com.example.nectar.ui.home
import ProductViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nectar.adapter.ProductAdapter
import com.example.nectar.data.ProductRepository
import com.example.nectar.data.api.RetrofitClient
import com.example.nectar.databinding.FragmentHomeBinding
import com.example.nectar.viewmodel.ProductViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    // ViewModel
    private val viewModel: ProductViewModel by viewModels {
        ProductViewModelFactory(
            ProductRepository(RetrofitClient.apiService)
        )
    }

    // Adapters
    private lateinit var exclusiveAdapter: ProductAdapter
    private lateinit var bestSellingAdapter: ProductAdapter
    private lateinit var groceryAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        setupSearch()
        observeData()

        // Fetch data
        viewModel.fetchProducts()
    }

    private fun setupRecyclerViews() {
        // Initialize adapters with empty lists
        exclusiveAdapter = ProductAdapter(emptyList())
        bestSellingAdapter = ProductAdapter(emptyList())
        groceryAdapter = ProductAdapter(emptyList())

        // Setup layout managers and attach adapters
        binding.recyclerViewExclusive.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewExclusive.adapter = exclusiveAdapter

        binding.recyclerViewBestSelling.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewBestSelling.adapter = bestSellingAdapter

        binding.recyclerViewGroceries.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewGroceries.adapter = groceryAdapter
    }

    private fun observeData() {
        viewModel.filteredList.observe(viewLifecycleOwner) { products ->
            exclusiveAdapter.updateList(products.filter { it.category.equals("Exclusive", true) })
            bestSellingAdapter.updateList(products.filter {
                it.category.equals(
                    "Best Selling",
                    true
                )
            })
            groceryAdapter.updateList(products.filter { it.category.equals("Groceries", true) })
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

        private fun setupSearch() {
            binding.searchBar.addTextChangedListener { editable ->
                viewModel.searchProducts(editable.toString())
            }
        }
    }
