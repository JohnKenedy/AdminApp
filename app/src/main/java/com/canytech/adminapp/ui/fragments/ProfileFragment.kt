package com.canytech.adminapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.canytech.adminapp.R
import com.canytech.adminapp.firestore.FireStoreClass
import com.canytech.adminapp.models.ProductTrending
import com.canytech.adminapp.ui.adapters.ProductItemsAdapter
import kotlinx.android.synthetic.main.fragment_edit_product.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment() {

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

        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        return root
    }

    override fun onResume() {
        super.onResume()
        getAllFeaturesProductsList()
    }

    fun successAllFeaturesProductList(productFeatureItemsList: ArrayList<ProductTrending>) {
        hideProgressDialog()

        rv_edit_products_feature_products.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_edit_products_feature_products.setHasFixedSize(true)

        val adapterAllProducts = ProductItemsAdapter(requireActivity(), productFeatureItemsList)
        rv_edit_products_feature_products.adapter = adapterAllProducts

    }

    private fun getAllFeaturesProductsList() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FireStoreClass().getAllFeaturesProductsList(this@ProfileFragment)
    }
}