package com.canytech.supermercado.ui.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.canytech.adminapp.R
import com.canytech.adminapp.models.User
import com.canytech.adminapp.ui.activities.BaseActivity
import com.canytech.adminapp.ui.activities.DashboardActivity
import com.canytech.adminapp.utils.Constants
import com.canytech.supermercado.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.profile_image
import java.io.IOException

open class UserProfileActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mUserDetails: User
    private var mSelectedImageFileUri: Uri? = null
    private var mUserProfileImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {

            // Get the user details fom intent as a ParcelableExtra
            mUserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        if (mUserDetails.profileCompleted == 0) {
            textView_user_profile_title.text = resources.getString(R.string.title_complete_profile)

            text_view_user_profile_name.text = mUserDetails.name
            text_view_user_profile_email.text = mUserDetails.email
            text_view_user_profile_number.text = mUserDetails.mobile.toString()
            text_view_user_profile_address.text = mUserDetails.address
        } else {
            setupActionBar()

            textView_user_profile_title.text = resources.getString(R.string.edit_profile)

            GlideLoader(this@UserProfileActivity).loadUserPicture(mUserDetails.image, profile_image)

            text_view_user_profile_name.text = mUserDetails.name
            text_view_user_profile_email.text = mUserDetails.email
            text_view_user_profile_address.text = mUserDetails.address


            if (mUserDetails.mobile != 0L) {
                text_view_user_profile_number.text = mUserDetails.mobile.toString()
            }
            if (mUserDetails.gender == Constants.MALE) {
                radio_btn_user_profile_male.isChecked = true
            } else {
                radio_btn_user_profile_female.isChecked = true
            }
        }

        profile_image.setOnClickListener(this@UserProfileActivity)
        btn_user_profile_submit.setOnClickListener(this@UserProfileActivity)
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_user_profile_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_white)
        }

        toolbar_user_profile_activity.setNavigationOnClickListener { onBackPressed() }
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
                Constants.showImageChooser(this@UserProfileActivity)
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
                    try {
                        //URI of selected image from phone storage
                        mSelectedImageFileUri = data.data!!

                        //Profile Image
                        GlideLoader(this@UserProfileActivity).loadUserPicture(mSelectedImageFileUri!!, profile_image)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this@UserProfileActivity,
                            resources.getString(R.string.image_selection_failed),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("Request Cancelled", "Image selection cancelled")
        }
    }

    private fun validateUserProfileDetails(): Boolean {
        return when {
            TextUtils.isEmpty(edit_text_register_number.text.toString().trim { it <= ' ' })
                    || TextUtils.isEmpty(
                edit_text_register_address.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.error_msg_complete_all_fields), true)
                false
            }
            else -> {
                true
            }
        }
    }

    fun userProfileUpdateSuccess() {
        hideProgressDialog()
        Toast.makeText(
            this@UserProfileActivity,
            resources.getString(R.string.profile_uptade_success),
            Toast.LENGTH_SHORT
        ).show()

        startActivity(Intent(this@UserProfileActivity, DashboardActivity::class.java))
        finish()

    }

    override fun onClick(v: View?) {

    }
}