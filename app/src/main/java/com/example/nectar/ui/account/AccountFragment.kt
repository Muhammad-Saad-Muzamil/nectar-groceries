package com.example.nectar.ui.auth

import OptionItem
import OptionsAdapter
import ProfileItem
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.nectar.R

import com.google.firebase.auth.FirebaseAuth

class AccountFragment : Fragment() {

    private lateinit var optionsRecyclerView: RecyclerView
    private lateinit var logoutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        optionsRecyclerView = view.findViewById(R.id.options_recycler_view)
        logoutButton = view.findViewById(R.id.logout_button)

        val items = listOf(
            ProfileItem(R.drawable.ic_account, "User", "user@gmail.com"),
            OptionItem(R.drawable.order_icon, "Order"),
            OptionItem(R.drawable.delivery_address, "Delivery Address"),
            OptionItem(R.drawable.payment_method, "Payment Methods"),
            OptionItem(R.drawable.promo_code, "Promo Code"),
            OptionItem(R.drawable.bell_icon, "Notifications"),
            OptionItem(R.drawable.help_icon, "Help"),
            OptionItem(R.drawable.my_details, "About")
        )
        val adapter = OptionsAdapter(items)
        optionsRecyclerView.adapter = adapter

        logoutButton.setOnClickListener {
            // Firebase logout
            FirebaseAuth.getInstance().signOut()

            // Redirect to login activity and clear back stack
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view
    }
}
