package com.example.moviedbapplication.ui.activity.main

import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.moviedbapplication.R
import com.example.moviedbapplication.common.base.BaseActivity
import com.example.moviedbapplication.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun setupUI() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        setupBottomNav()
    }

    private fun setupBottomNav() {
        val visibleDestinations = setOf(
            R.id.characterFragment,
            R.id.episodeFragment
        )

        binding.bottomNavigationView.apply {
            setupWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                isVisible = destination.id in visibleDestinations
            }

            setOnItemSelectedListener { menuItem ->
                NavigationUI.onNavDestinationSelected(menuItem, navController)
                navController.popBackStack(menuItem.itemId, false)
                true
            }
        }
    }
}