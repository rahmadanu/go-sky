package com.binar.gosky.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.binar.gosky.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment

        navController = navHostFragment.navController

        bottomNav = findViewById(R.id.bottom_nav_view)

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment,
            R.id.wishlistFragment,
            R.id.wishlistFragment,
            R.id.historyTicketFragment,
            R.id.accountFragment
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNav.setupWithNavController(navController)
        supportActionBar?.hide()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.searchResultFragment -> hideBottomNav(true)
                R.id.loginFragment -> hideBottomNav(true)
                R.id.registerFragment -> hideBottomNav(true)
                R.id.editProfileFragment -> hideBottomNav(true)
                R.id.confirmationTicketFragment -> hideBottomNav(true)
                R.id.detailTicketFragment -> hideBottomNav(true)
                R.id.notificationFragment -> hideBottomNav(true)
                R.id.editConfirmationTicketFragment -> hideBottomNav(true)
                R.id.resetPasswordFragment -> hideBottomNav(true)
                else -> hideBottomNav(false)
            }
        }
    }

    private fun hideBottomNav(hide: Boolean) {
        if (hide) {
            bottomNav.visibility = View.GONE
        } else {
            bottomNav.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}