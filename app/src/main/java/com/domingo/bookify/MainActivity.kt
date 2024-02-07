package com.domingo.bookify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.domingo.bookify.databinding.ActivityMainBinding
import com.domingo.bookify.fragments.HomeFragment
import com.domingo.bookify.fragments.ProfileFragment
import com.domingo.bookify.fragments.SavedFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = customColor(R.color.white)
        toolbar = binding.toolbar
        setUpToolBar(getString(R.string.app_name))

        loadFragment(HomeFragment(), "Home")

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment(), "Home")
                    true
                }

                R.id.saved -> {
                    loadFragment(SavedFragment(), "Bookmark")
                    true
                }

                R.id.profile -> {
                    loadFragment(ProfileFragment(), "Profile")
                    true
                }

                else -> false
            }
        }

        // Add a listener to update the title when the back stack changes
        supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.container)
            fragment?.let {
                supportActionBar?.title =
                    it.arguments?.getString("title") ?: getString(R.string.app_name)
            }
        }
    }

    private fun customColor(colorResourceId: Int): Int {
        return ContextCompat.getColor(this, colorResourceId)
    }

    private fun setUpToolBar(title: String) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
    }

    private fun loadFragment(fragment: Fragment, title: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        // Pass title as an argument to the fragment
        val args = Bundle()
        args.putString("title", title)
        fragment.arguments = args
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
