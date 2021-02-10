package com.canytech.adminapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.canytech.adminapp.R
import com.canytech.adminapp.models.Order
import com.canytech.supermercado.utils.GlideLoader
import kotlinx.android.synthetic.main.item_list_layout.view.*

open class MyOrdersListAdapter(
    private val context: Context,
    private var list: ArrayList<Order>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list_layout, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            GlideLoader(context).loadUserPicture(model.image, holder.itemView.item_img_product)

            holder.itemView.item_list_title_product.text = model.title
            holder.itemView.item_list_price_product.text = "$${model.total_amount}"

            holder.itemView.iv_delete_product.visibility = View.GONE

        }
    }

    override fun getItemCount(): Int = list.size

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

