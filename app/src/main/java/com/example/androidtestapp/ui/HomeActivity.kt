package com.example.androidtestapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.androidtestapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {

    private var toolbar: ActionBar? = null
    private var key : String? = null
    private var token : String? = null
    private val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        key = intent.extras?.get("key")?.toString()
        token = intent.extras?.get("token")?.toString()
        bundle.putString("key", key)
        bundle.putString("token", token)

        toolbar = supportActionBar
        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        toolbar!!.title = "Map"
        loadFragment(LocationFragment(), bundle)
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var fragment: Fragment
            when (item.itemId) {
                R.id.navigation_map -> {
                    toolbar!!.title = "Map"
                    fragment = LocationFragment()
                    loadFragment(fragment, bundle)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    toolbar!!.title = "My Profile"
                    fragment = ProfileFragment()
                    loadFragment(fragment, bundle)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun loadFragment(fragment: Fragment, bundle: Bundle) {
        // load fragment
        fragment.arguments = bundle
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
