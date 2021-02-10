package com.canytech.adminapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.canytech.adminapp.R
import com.canytech.adminapp.firestore.FireStoreClass
import com.canytech.adminapp.models.ProductCategories
import com.canytech.adminapp.ui.adapters.CategoryItemsAdapter
import kotlinx.android.synthetic.main.fragment_edit_category_product.*
import kotlinx.android.synthetic.main.fragment_edit_features_product.*

class CategoryFragment : BaseFragment() {

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

        val root = inflater.inflate(R.layout.fragment_edit_category_product, container, false)

        return root
    }

    override fun onResume() {
        super.onResume()
        getAllCategoryProductsList()
    }


    fun successAllCategoryProductList(productCategoryItemsList: ArrayList<ProductCategories>) {
        hideProgressDialog()

        rv_edit_category_products_all_products.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_edit_category_products_all_products.setHasFixedSize(true)

        val adapterAllProducts = CategoryItemsAdapter(requireActivity(), productCategoryItemsList,
            this@CategoryFragment)
        rv_edit_category_products_all_products.adapter = adapterAllProducts

    }

    private fun getAllCategoryProductsList() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FireStoreClass().getAllCategoryProductsList(this@CategoryFragment)
    }
}