package com.canytech.adminapp.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.canytech.adminapp.R
import com.canytech.adminapp.firestore.FireStoreClass
import com.canytech.adminapp.models.ProductCategories
import com.canytech.adminapp.utils.Constants
import com.canytech.supermercado.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_add_category_product.*
import kotlinx.android.synthetic.main.activity_add_feature_product.*
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.IOException

class AddCategoryActivity : BaseActivity(), View.OnClickListener {

    private var mCategorySelectedImageFileURI: Uri? = null
    private var mCategoryProductImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category_product)
        setupActionBar()

        imageView_add_update_category_product.setOnClickListener(this)
        btn_add_product_category_submit.setOnClickListener(this)
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_add_category_product_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_white)
        }

        toolbar_add_category_product_activity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.imageView_add_update_category_product -> {
                    if (ContextCompat.checkSelfPermission(
                            this, Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Constants.showImageChooser(this@AddCategoryActivity)
                    } else {
                        ActivityCompat.requestPermissions(
                            this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }

                R.id.btn_add_product_category_submit -> {
                    if (validateCategoryProductDetails()) {
                        uploadCategoryProductImage()
                    }
                }
            }
        }
    }

    private fun uploadCategoryProductImage() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FireStoreClass().uploadImageToCloudStorage(
            this,
            mCategorySelectedImageFileURI,
            Constants.CATEGORY_IMAGE
        )
    }

    fun productCategoryUploadSuccess() {
        hideProgressDialog()
        Toast.makeText(
            this@AddCategoryActivity,
            resources.getString(R.string.category_uploaded_success_message),
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    fun imageCategoryUploadSuccess(imageURL: String) {
//        hideProgressDialog()
//        showErrorSnackBar("Category image is uploaded successfully. Image URL: $imageURL", false)

        mCategoryProductImageURL = imageURL

        uploadCategoryProductDetails()

    }

    private fun uploadCategoryProductDetails() {

        val username = this.getSharedPreferences(
            Constants.MYGROCERYSTORE_PREFERENCES, Context.MODE_PRIVATE)
            .getString(Constants.LOGGED_IN_USERNAME, "")!!

        val CategoryProduct = ProductCategories(
            edit_text_product_category_title.text.toString().trim { it <= ' ' },
            mCategoryProductImageURL
        )

        FireStoreClass().uploadCategoryDetails(this, CategoryProduct)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //showErrorSnackBar("The permission is granted.", false)
                Constants.showImageChooser(this)
            } else {

                Toast.makeText(
                    this, resources.getString(R.string.storage_permissiob_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {

                    imageView_add_update_category_product.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_baseline_add_location_white
                        )
                    )

                    mCategorySelectedImageFileURI = data.data!!

                    try {
                        GlideLoader(this).loadUserPicture(
                            mCategorySelectedImageFileURI!!,
                            imageView_product_category_image
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("Request Cancelled", "Image selection cancelled")
        }
    }

    private fun validateCategoryProductDetails(): Boolean {
        return when {
            mCategorySelectedImageFileURI == null -> {
                showErrorSnackBar(
                    resources.getString(R.string.error_msg_select_category_image), true
                )
                false
            }

            TextUtils.isEmpty(edit_text_product_category_title.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_enter_category_title), true)
                false
            }
            else -> {
                true
            }
        }
    }
}