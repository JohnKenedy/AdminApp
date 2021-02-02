package com.canytech.adminapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.canytech.adminapp.R
import com.canytech.adminapp.firestore.FireStoreClass
import com.canytech.adminapp.models.ProductTrending
import com.canytech.adminapp.ui.adapters.ProductItemsAdapter
import kotlinx.android.synthetic.main.fragment_edit_trending_product.*

class ProductItemsFragment : BaseFragment() {

//    private lateinit var notificationsViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        notificationsViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_edit_trending_product, container, false)

        return root
    }

    override fun onResume() {
        super.onResume()
        getAllProductsList()
    }

    fun successAllProductList(productItemsList: ArrayList<ProductTrending>) {
        hideProgressDialog()

        rv_edit_products_all_products.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_edit_products_all_products.setHasFixedSize(true)

        val adapterAllProducts = ProductItemsAdapter(requireActivity(), productItemsList)
        rv_edit_products_all_products.adapter = adapterAllProducts

    }

    private fun getAllProductsList() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FireStoreClass().getAllProductsList(this@ProductItemsFragment)
    }





}