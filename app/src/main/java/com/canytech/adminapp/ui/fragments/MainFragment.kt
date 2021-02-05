package com.canytech.adminapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.canytech.adminapp.R
import com.canytech.adminapp.ui.activities.AddCategoryActivity
import com.canytech.adminapp.ui.activities.AddFeatureProductActivity
import com.canytech.adminapp.ui.activities.AddTrendingProductActivity

class MainFragment : Fragment() {

//    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_products, container, false)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_add_trending_product -> {
                startActivity(Intent(activity, AddTrendingProductActivity::class.java))
                return true
            }
            R.id.action_add_feature_product -> {
                startActivity(Intent(activity, AddFeatureProductActivity::class.java))
                return true
            }
            R.id.action_add_category -> {
                startActivity(Intent(activity, AddCategoryActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
