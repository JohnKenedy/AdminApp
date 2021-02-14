package com.canytech.adminapp.ui.activities

import android.os.Bundle
import com.canytech.adminapp.R
import com.canytech.adminapp.models.Product
import com.canytech.supermercado.models.CartItem


class CartListActivity : BaseActivity() {

    private lateinit var mProductList: ArrayList<Product>
    private lateinit var mCartListItems: ArrayList<CartItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list)


    }
}