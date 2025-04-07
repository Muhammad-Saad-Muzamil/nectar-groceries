package com.example.nectar


import CartFragment
import ExploreFragment
import HomeFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nectar.ui.auth.AccountFragment


import com.example.nectar.ui.favorites.FavoriteFragment

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)



        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)


        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_shop -> loadFragment(HomeFragment())
                R.id.nav_explore -> loadFragment(ExploreFragment())
                R.id.nav_cart -> loadFragment(CartFragment())
                R.id.nav_favorites -> loadFragment(FavoriteFragment())
                R.id.nav_account -> loadFragment(AccountFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
