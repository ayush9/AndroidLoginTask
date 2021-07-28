package com.example.androidtestapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.androidtestapp.viewmodel.HomeViewModel
import com.example.androidtestapp.R
import kotlinx.android.synthetic.main.profile_view.*
import java.util.*

class ProfileFragment() : Fragment() {

    private val homeViewModel: HomeViewModel by lazy {
        getViewModelProvider(this, null).get(
            HomeViewModel::class.java
        )
    }
    private var key : String? = null
    private var token : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        key = requireArguments().getString("key")
        token = requireArguments().getString("token")
        return inflater.inflate(R.layout.profile_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.fetchUserDetails(key, token)
        homeViewModel.userResponse.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                name.text = it.display_name
                email.text = it.email
                days.text = getDifferenceDays(it.created_at, Date(System.currentTimeMillis())).toString()
            }
        })
        logout_btn.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDifferenceDays(d1: Date, d2: Date): Int {
        var daysdiff = 0
        val diff: Long = d2.time - d1.time
        val diffDays = diff / (24 * 60 * 60 * 1000) + 1
        daysdiff = diffDays.toInt()
        return daysdiff
    }

    private fun getViewModelProvider(
        fragment: Fragment,
        factory: ViewModelProvider.Factory?
    ): ViewModelProvider {
        return ViewModelProviders.of(fragment, factory)
    }

}
