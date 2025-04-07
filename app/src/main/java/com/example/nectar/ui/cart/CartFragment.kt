import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nectar.R
import com.example.nectar.ui.cart.CartItem
import com.example.nectar.ui.cart.CartItemAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CartFragment : Fragment() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var checkoutButton: Button
    private lateinit var totalPrice: TextView
    private lateinit var cartAdapter: CartItemAdapter

    private var cartItems = mutableListOf(
        CartItem("Bell Pepper Red", R.drawable.bell_pepper, "1kg, Price", 4.99, 1),
        CartItem("Egg Chicken Red", R.drawable.dairy_eggs, "4pcs, Price", 1.99, 1),
        CartItem("Organic Bananas", R.drawable.banana, "12kg, Price", 3.00, 1),
        CartItem("Ginger", R.drawable.ginger, "250gm, Price", 2.99, 1)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find views by ID
        cartRecyclerView = view.findViewById(R.id.cartRecyclerView)
        checkoutButton = view.findViewById(R.id.checkoutButton)
        totalPrice = view.findViewById(R.id.totalPrice)

        // Setup RecyclerView
        cartAdapter = CartItemAdapter(cartItems) { updateTotalPrice() }
        cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        cartRecyclerView.adapter = cartAdapter

        // Update total price initially
        updateTotalPrice()

        // Checkout button click listener
        checkoutButton.setOnClickListener {
            val totalCost = calculateTotalCost()
            val checkoutBottomSheet = CheckoutBottomSheetFragment.newInstance(totalCost)
            checkoutBottomSheet.show(parentFragmentManager, "CheckoutBottomSheet")
        }
    }

    private fun updateTotalPrice() {
        totalPrice.text = "$${String.format("%.2f", calculateTotalCost())}"
    }

    private fun calculateTotalCost(): Double {
        var total = 0.0
        for (item in cartItems) {
            total += item.price * item.quantity
        }
        return total
    }
}
