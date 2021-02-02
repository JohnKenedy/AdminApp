package com.canytech.adminapp.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.canytech.adminapp.R
import com.canytech.adminapp.firestore.FireStoreClass
import com.canytech.adminapp.models.ProductTrending
import com.canytech.adminapp.ui.adapters.ProductFeatureItemsAdapter
import kotlinx.android.synthetic.main.fragment_edit_features_product.*

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

    override fun onResume() {
        super.onResume()
        getAllFeaturesProductsList()
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
            FireStoreClass().deleteFeatureProduct(this, productID)

            dialogInterface.dismiss()
        }

        builder.setNegativeButton(resources.getString(R.string.no)) { dialogInterface, _ ->

            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun productFeatureDeleteSuccess() {
        hideProgressDialog()

        Toast.makeText(requireActivity(),resources.getString(R.string.product_delete_success),
            Toast.LENGTH_SHORT).show()

        getAllFeaturesProductsList()
    }

    fun successAllFeaturesProductList(productFeatureItemsList: ArrayList<ProductTrending>) {
        hideProgressDialog()

        rv_edit_products_feature_products.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_edit_products_feature_products.setHasFixedSize(true)

        val adapterAllProducts = ProductFeatureItemsAdapter(requireActivity(), productFeatureItemsList,
            this@ProductsFeatureFragment)
        rv_edit_products_feature_products.adapter = adapterAllProducts

    }

    private fun getAllFeaturesProductsList() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FireStoreClass().getAllFeaturesProductsList(this@ProductsFeatureFragment)
    }
}