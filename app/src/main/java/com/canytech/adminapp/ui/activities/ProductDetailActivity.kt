package com.canytech.adminapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.canytech.adminapp.R
import com.canytech.adminapp.firestore.FireStoreClass
import com.canytech.adminapp.models.ProductTrending
import com.canytech.supermercado.utils.Constants
import com.canytech.supermercado.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : BaseActivity() {

    private var mProductId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        setupActionBar()

        if (intent.hasExtra(Constants.EXTRA_PRODUCT_ID)) {
            mProductId = intent.getStringExtra(Constants.EXTRA_PRODUCT_ID)!!
            Log.i("Product id", mProductId)
        }

        getProductDetails()
        getFeatureProductsDetails()

    }

    private fun getProductDetails() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FireStoreClass().getProductsDetails(this, mProductId)
    }

    private fun getFeatureProductsDetails() {
        FireStoreClass().getFeatureProductsDetails(this, mProductId)
    }

    fun productDetailsSuccess(product: ProductTrending) {
        hideProgressDialog()
        GlideLoader(this@ProductDetailActivity).loadProductPicture(
            product.image, iv_product_detail_image)

        item_title_product.text = product.title
        item_old_price_product.text = product.old_price
        item_price_product.text = product.price
        item_in_out_stock_quantity.text = product.stock_quantity
        textView_item_unit.text = product.unit
        til_product_description.text = product.description
    }

    private fun setupActionBar() {

        setSupportActionBar(top)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_white)
        }

        top.setNavigationOnClickListener { onBackPressed() }
    }
}