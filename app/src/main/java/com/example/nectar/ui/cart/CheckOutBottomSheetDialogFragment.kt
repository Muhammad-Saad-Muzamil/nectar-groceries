import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.nectar.OrderSuccessActivity
import com.example.nectar.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CheckoutBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var totalCostTextView: TextView
    private lateinit var deliveryMethodTextView: TextView
    private lateinit var paymentMethodTextView: TextView
    private lateinit var promoCodeTextView: TextView
    private lateinit var placeOrderButton: Button
    private lateinit var closeButton: ImageView
    private var totalCost: Double = 0.0  // Default

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_checkout, container, false)

        // Initialize UI elements
        totalCostTextView = view.findViewById(R.id.TotalCostTextView)
        deliveryMethodTextView = view.findViewById(R.id.DeliveryMethodTextView)
        paymentMethodTextView = view.findViewById(R.id.PaymentMethodTextView)
        promoCodeTextView = view.findViewById(R.id.PromoCodeTextView)
        placeOrderButton = view.findViewById(R.id.PlaceOrder)
        closeButton = view.findViewById(R.id.closeButton)

        // Get total cost from arguments
        arguments?.let {
            totalCost = it.getDouble("TOTAL_COST", 0.0)
        }

        // Format and display total cost
        totalCostTextView.text = String.format("$%.2f", totalCost)

        // Handle close button click
        closeButton.setOnClickListener {
            dismiss()  // Close the bottom sheet
        }

        // Handle delivery method selection
        deliveryMethodTextView.setOnClickListener {
            showDeliveryOptions()
        }

        // Handle payment method selection
        paymentMethodTextView.setOnClickListener {
            showPaymentOptions()
        }

        // Handle promo code selection
        promoCodeTextView.setOnClickListener {
            showPromoCodeOptions()
        }

        // Handle place order button click
        placeOrderButton= view.findViewById(R.id.PlaceOrder)
        placeOrderButton.setOnClickListener {
            val intent = Intent(requireContext(), OrderSuccessActivity::class.java)
            startActivity(intent)
            dismiss() // Close the bottom sheet
        }

        return view
    }

    private fun showDeliveryOptions() {
        val options = arrayOf("Standard Delivery", "Express Delivery")
        AlertDialog.Builder(requireContext())
            .setTitle("Select Delivery Method")
            .setItems(options) { _, which ->
                deliveryMethodTextView.text = options[which]
            }
            .show()
    }

    private fun showPaymentOptions() {
        val options = arrayOf("Cash on Delivery", "MasterCard", "PayPal")
        AlertDialog.Builder(requireContext())
            .setTitle("Select Payment Method")
            .setItems(options) { _, which ->
                paymentMethodTextView.text = options[which]
            }
            .show()
    }

    private fun showPromoCodeOptions() {
        val options = arrayOf("10% OFF", "Free Shipping", "No Discount")
        AlertDialog.Builder(requireContext())
            .setTitle("Pick a Discount")
            .setItems(options) { _, which ->
                promoCodeTextView.text = options[which]
            }
            .show()
    }

    private fun placeOrder() {
        // Example: Show confirmation message
        Toast.makeText(requireContext(), "Order Placed Successfully!", Toast.LENGTH_SHORT).show()
        dismiss()  // Close the bottom sheet
    }

    companion object {
        fun newInstance(totalCost: Double): CheckoutBottomSheetFragment {
            val fragment = CheckoutBottomSheetFragment()
            val args = Bundle()
            args.putDouble("TOTAL_COST", totalCost)
            fragment.arguments = args
            return fragment
        }
    }
}
