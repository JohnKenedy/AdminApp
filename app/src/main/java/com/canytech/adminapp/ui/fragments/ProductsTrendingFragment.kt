package com.canytech.adminapp.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.canytech.adminapp.R
import com.canytech.adminapp.firestore.FireStoreClass
import com.canytech.adminapp.models.Product
import com.canytech.adminapp.ui.adapters.ProductTrendingItemsAdapter
import kotlinx.android.synthetic.main.fragment_edit_trending_product.*

class ProductsTrendingFragment : BaseFragment() {

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
    }

    fun deleteProduct(productID: String) {
        showAlertDialogToDeleteProduct(productID)
    }

    private fun showAlertDialogToDeleteProduct(productID: String) {

        val builder = AlertDialog.Builder(requireActivity())

        builder.setTitle(resources.getString(R.string.delete_dialog_title))
        builder.setMessage(resources.getString(R.string.delete_dialog_message))
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton(resources.getString(R.string.yes)) { dialogInterface, _ ->
            showProgressDialog(resources.getString(R.string.please_wait))

            FireStoreClass().deleteTrendingProduct(this, productID)

            dialogInterface.dismiss()
        }

        builder.setNegativeButton(resources.getString(R.string.no)) { dialogInterface, _ ->

            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun productTrendingDeleteSuccess() {
        hideProgressDialog()

        Toast.makeText(
            requireActivity(), resources.getString(R.string.product_delete_success),
            Toast.LENGTH_SHORT
        ).show()

    }

    fun successAllProductList(productItemsList: ArrayList<Product>) {
        hideProgressDialog()

        rv_edit_products_all_products.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_edit_products_all_products.setHasFixedSize(true)

        val adapterAllProducts = ProductTrendingItemsAdapter(
            requireActivity(), productItemsList,
            this@ProductsTrendingFragment
        )
        rv_edit_products_all_products.adapter = adapterAllProducts
    }
}