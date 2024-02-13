package com.example.onlinestoretest.presentation.main

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.onlinestoretest.utils.CenteredTitleToolbar
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

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

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
                    binding.toolbar.titleTextView?.gravity = Gravity.CENTER_VERTICAL or Gravity.START
                    supportActionBar?.title = resources.getString(R.string.favorite)
                }
                else -> {
                    supportActionBar?.title = ""
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
        }

        supportActionBar?.let { actionBar ->
            actionBar.title = resources.getString(R.string.login)

            val centeredTitleToolbar = binding.toolbar as CenteredTitleToolbar
            centeredTitleToolbar.titleTextView?.let { titleTextView ->
                titleTextView.typeface = ResourcesCompat.getFont(this, R.font.sf_pro_display_medium)
                titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                titleTextView.setTextColor(Color.BLACK)

                val layoutParams = titleTextView.layoutParams as Toolbar.LayoutParams
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
                layoutParams.bottomMargin = 10.dpToPx(this)
                titleTextView.layoutParams = layoutParams
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}