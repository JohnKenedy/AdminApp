package com.canytech.adminapp.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.canytech.adminapp.R
import com.canytech.adminapp.models.ProductCategories
import com.canytech.adminapp.models.ProductTrending
import com.canytech.adminapp.ui.activities.ProductDetailActivity
import com.canytech.adminapp.ui.fragments.CategoryFragment
import com.canytech.adminapp.ui.fragments.ProductsFeatureFragment
import com.canytech.supermercado.utils.Constants
import com.canytech.supermercado.utils.GlideLoader
import kotlinx.android.synthetic.main.item_categories.view.*
import kotlinx.android.synthetic.main.item_list_layout.*
import kotlinx.android.synthetic.main.item_list_layout.view.*

open class CategoryItemsAdapter (
    private val context: Context,
    private var allProductList: ArrayList<ProductCategories>,
    private val fragment: CategoryFragment
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AllCategoryViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_categories, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = allProductList[position]

        if (holder is AllCategoryViewHolder) {

            GlideLoader(context).loadProductPicture(

                model.img_category, holder.itemView.img_category
            )
            holder.itemView.title_category.text = model.category_title

        }
    }

    override fun getItemCount(): Int {
        return allProductList.size
    }

    class AllCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view)
}