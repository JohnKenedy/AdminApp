package com.canytech.adminapp.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.canytech.adminapp.R
import com.canytech.adminapp.models.Product
import com.canytech.adminapp.ui.activities.ProductDetailActivity
import com.canytech.adminapp.ui.fragments.ProductsTrendingFragment
import com.canytech.adminapp.utils.Constants
import com.canytech.supermercado.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_product_detail.view.*
import kotlinx.android.synthetic.main.item_list_layout.view.*
import kotlinx.android.synthetic.main.item_list_layout.view.item_old_price_product

open class ProductTrendingItemsAdapter (
    private val context: Context,
    private var allProductList: ArrayList<Product>,
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
            holder.itemView.item_list_title_product.text = model.title
            holder.itemView.item_old_price_product.text = model.old_price
            holder.itemView.textView_item_list_category.text = model.category
            holder.itemView.textView_item_list_quantity.text = model.stock_quantity

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