package com.canytech.adminapp.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.canytech.adminapp.R
import com.canytech.adminapp.models.ProductTrending
import com.canytech.adminapp.ui.activities.ProductDetailActivity
import com.canytech.adminapp.ui.fragments.ProductsFeatureFragment
import com.canytech.adminapp.ui.fragments.ProductsTrendingFragment
import com.canytech.supermercado.utils.Constants
import com.canytech.supermercado.utils.GlideLoader
import kotlinx.android.synthetic.main.item_list_layout.*
import kotlinx.android.synthetic.main.item_list_layout.view.*

open class ProductTrendingItemsAdapter (
    private val context: Context,
    private var allProductList: ArrayList<ProductTrending>,
    private val fragment: ProductsTrendingFragment
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

        holder.itemView.btn_item_list_layout_view_product.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(Constants.EXTRA_PRODUCT_ID, model.product_id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return allProductList.size
    }

    class AllProductsViewHolder(view: View) : RecyclerView.ViewHolder(view)
}