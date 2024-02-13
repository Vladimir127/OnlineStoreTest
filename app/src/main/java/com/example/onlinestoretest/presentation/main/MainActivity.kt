package com.example.onlinestoretest.presentation.main

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.onlinestoretest.R
import com.example.onlinestoretest.databinding.ActivityMainBinding
import com.example.onlinestoretest.utils.dpToPx

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()

        initNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)

        // Убираем границу между StatusBar и ActionBar, а также устаналиваем белый цвет
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))

        supportActionBar?.let { actionBar ->
            actionBar.title = resources.getString(R.string.login)
        }

        val centeredTitleToolbar = binding.toolbar
        centeredTitleToolbar.titleTextView?.let { titleTextView ->
            val layoutParams = titleTextView.layoutParams as Toolbar.LayoutParams
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
            layoutParams.bottomMargin = 10.dpToPx(this)
            titleTextView.layoutParams = layoutParams
        }
    }

    private fun initNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        // В зависимости от фрагмента, к которому мы переходим. настраиваем внешний вид кастомного Toolbar:
        // видимость правой и левой иконок, выравнивание и текст заголовка
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment -> {
                    binding.toolbar.backButton?.visibility = View.GONE
                    binding.toolbar.titleTextView?.gravity = Gravity.CENTER
                    binding.toolbar.shareButton?.visibility = View.GONE
                    supportActionBar?.title = resources.getString(R.string.main)
                }

                R.id.catalogFragment -> {
                    binding.toolbar.backButton?.visibility = View.GONE
                    binding.toolbar.titleTextView?.gravity = Gravity.CENTER
                    binding.toolbar.shareButton?.visibility = View.GONE
                    supportActionBar?.title = resources.getString(R.string.catalog)
                }

                R.id.cartFragment -> {
                    binding.toolbar.backButton?.visibility = View.GONE
                    binding.toolbar.titleTextView?.gravity = Gravity.CENTER
                    binding.toolbar.shareButton?.visibility = View.GONE
                    supportActionBar?.title = resources.getString(R.string.cart)
                }

                R.id.promotionsFragment -> {
                    binding.toolbar.backButton?.visibility = View.GONE
                    binding.toolbar.titleTextView?.gravity = Gravity.CENTER
                    binding.toolbar.shareButton?.visibility = View.GONE
                    supportActionBar?.title = resources.getString(R.string.promotions)
                }

                R.id.profileFragment -> {
                    binding.toolbar.backButton?.visibility = View.GONE
                    binding.toolbar.titleTextView?.gravity = Gravity.CENTER
                    binding.toolbar.shareButton?.visibility = View.GONE
                    supportActionBar?.title = resources.getString(R.string.profile)
                }

                R.id.productFragment -> {
                    binding.toolbar.backButton?.visibility = View.VISIBLE
                    binding.toolbar.shareButton?.visibility = View.VISIBLE
                    binding.toolbar.backButton?.setOnClickListener {
                        onBackPressed()
                    }
                    supportActionBar?.title = ""
                }

                R.id.favoritesFragment -> {
                    binding.toolbar.backButton?.visibility = View.VISIBLE
                    binding.toolbar.backButton?.setOnClickListener {
                        onBackPressed()
                    }
                    binding.toolbar.titleTextView?.gravity =
                        Gravity.CENTER_VERTICAL or Gravity.START
                    supportActionBar?.title = resources.getString(R.string.favorite)
                }

                else -> {
                    supportActionBar?.title = ""
                }
            }
        }
    }
}