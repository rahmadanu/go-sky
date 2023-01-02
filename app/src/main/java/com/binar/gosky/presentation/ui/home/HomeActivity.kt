package com.binar.gosky.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.binar.gosky.R
import com.binar.gosky.databinding.ActivityHomeBinding
import com.binar.gosky.presentation.ui.account.AccountViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNav: BottomNavigationView

    private val accountViewModel: AccountViewModel by viewModels()

    private lateinit var binding: ActivityHomeBinding


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
            R.id.historyTicketFragment,
            R.id.accountFragment
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNav.setupWithNavController(navController)
        supportActionBar?.hide()

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_editConfirmationTicketFragment)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.searchResultFragment -> {
                    hideBottomNav(true)
                    hideNav(true)
                }
                R.id.loginFragment -> {
                    hideBottomNav(true)
                    hideNav(true)
                }
                R.id.registerFragment -> {
                    hideBottomNav(true)
                    hideNav(true)
                }
                R.id.editProfileFragment -> {
                    hideBottomNav(true)
                    hideNav(true)
                }
                R.id.confirmationTicketFragment -> {
                    hideBottomNav(true)
                    hideNav(true)
                }
                R.id.detailTicketFragment -> {
                    hideBottomNav(true)
                    hideNav(true)
                }
                R.id.notificationFragment -> {
                    hideBottomNav(true)
                    hideNav(true)
                }
                R.id.editConfirmationTicketFragment -> {
                    hideBottomNav(true)
                    hideNav(true)
                }
                else -> {
                    hideBottomNav(false)
                    hideNav(false)
                }
            }
        }

        accountViewModel.checkIfUserIsAdmin().observe(this) {
            val isAdmin = it.equals("ADMIN")
            showFabIfUserIsAdmin(isAdmin)
        }
    }


    private fun showFabIfUserIsAdmin(isAdmin: Boolean) {
        if (!isAdmin) {
            val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
            bottomNav.menu.removeItem(R.id.placeholder)
            val fab = findViewById<FloatingActionButton>(R.id.fab)
            fab.isVisible = false
        } else {
            val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
            bottomNav.menu.getItem(2).isEnabled = true
            val fab = findViewById<FloatingActionButton>(R.id.fab)
            fab.isVisible = true
        }
    }

    private fun hideNav(hide: Boolean){
        val bottom = findViewById<BottomAppBar>(R.id.bottom_nav_viewx)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        bottom.isVisible = !hide
        fab.isVisible = !hide
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
