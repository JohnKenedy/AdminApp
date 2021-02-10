package com.canytech.adminapp.ui.fragments

import android.os.Bundle
import android.view.*
import com.canytech.adminapp.R

class ProductsFeatureFragment : BaseFragment() {

//    private lateinit var notificationsViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_edit_features_product, container, false)

        return root
    }
}