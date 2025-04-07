package com.example.nectar.ui.favorites

import FavoriteItemAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nectar.R


class FavoriteFragment : Fragment() {

    private lateinit var favoritesRecyclerView: RecyclerView
    private lateinit var addAllToCartButton: Button
    private lateinit var favoriteAdapter: FavoriteItemAdapter

    private val favoriteItems = mutableListOf(
        FavoriteItem("Sprite Can", R.drawable.sprite, "325ml, Price", 1.50),
        FavoriteItem("Diet Coke", R.drawable.pepsi, "355ml, Price", 1.99),
        FavoriteItem("Apple & Grape Juice", R.drawable.sprite, "2L, Price", 15.50),
        FavoriteItem("Coca Cola Can", R.drawable.coke, "375ml, Price", 4.99),
        FavoriteItem("Pepsi Can", R.drawable.pepsi, "330ml, Price", 4.99 ),
        FavoriteItem("apple juice", R.drawable.sprite_can, "375ml, Price", 6.99),
        FavoriteItem("pepsi", R.drawable.pepsi_can, "305ml, Price", 1.99),
        FavoriteItem("coke", R.drawable.diet_coke, "475ml, Price", 5.99),
        FavoriteItem("diet coke", R.drawable.pepsi_can, "575ml, Price", 6.99),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritesRecyclerView = view.findViewById(R.id.favoritesRecyclerView)
        addAllToCartButton = view.findViewById(R.id.addAllToCartButton)

        // Setup RecyclerView
        favoriteAdapter = FavoriteItemAdapter(favoriteItems) { _ ->
            // Handle item click (e.g., navigate to details)
        }
        favoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        favoritesRecyclerView.adapter = favoriteAdapter

        // Add all to cart functionality
        addAllToCartButton.setOnClickListener {


        }
    }



}

