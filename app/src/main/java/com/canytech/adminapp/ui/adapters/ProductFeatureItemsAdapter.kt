package com.canytech.adminapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.canytech.adminapp.R
import com.canytech.adminapp.models.ProductTrending
import com.canytech.adminapp.ui.fragments.ProductsFeatureFragment
import com.canytech.supermercado.utils.GlideLoader
import kotlinx.android.synthetic.main.item_list_layout.*
import kotlinx.android.synthetic.main.item_list_layout.view.*

open class ProductFeatureItemsAdapter (
    private val context: Context,
    private var allProductList: ArrayList<ProductTrending>,
    private val fragment: ProductsFeatureFragment
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AllProductsViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = allProductList[position]

        if (holder is AllProductsViewHolder) {

            GlideLoader(context).loadProductPicture(

                model.image, holder.itemView.item_img_product)
            holder.itemView.item_title_product.text = model.title
            holder.itemView.textView_item_unit.text = model.unit
            holder.itemView.item_price_product.text = model.price
            holder.itemView.item_old_price_product.text = model.old_price
            holder.itemView.textView_item_category.text = model.category
            holder.itemView.textView_item_quantity.text = model.stock_quantity

            holder.itemView.iv_delete_product.setOnClickListener {
                fragment.deleteProduct(model.product_id)
            }
        }
    }

    override fun getItemCount(): Int {
        return allProductList.size
    }

    class AllProductsViewHolder(view: View) : RecyclerView.ViewHolder(view)
}