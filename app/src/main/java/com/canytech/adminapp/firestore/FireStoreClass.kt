package com.canytech.adminapp.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import com.canytech.adminapp.models.Order
import com.canytech.adminapp.models.Product
import com.canytech.adminapp.models.ProductCategories
import com.canytech.adminapp.models.User
import com.canytech.adminapp.ui.activities.*
import com.canytech.adminapp.ui.fragments.CategoryFragment
import com.canytech.adminapp.ui.fragments.OrdersFragment
import com.canytech.adminapp.ui.fragments.ProductsFeatureFragment
import com.canytech.adminapp.ui.fragments.ProductsTrendingFragment
import com.canytech.adminapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FireStoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {

        mFireStore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                //Here call a function of base activity for transferring the result to it.
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering the user.",
                    e
                )
            }
    }

    fun getCurrentUserID(): String {

        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }

    fun getUserDetails(activity: Activity) {
        // Here the collection name from which we wants the data
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                Log.i(activity.javaClass.simpleName, document.toString())

                val user = document.toObject(User::class.java)!!

                val sharedPreferences =
                    activity.getSharedPreferences(
                        Constants.MYGROCERYSTORE_PREFERENCES,
                        Context.MODE_PRIVATE
                    )

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(

                    Constants.LOGGED_IN_USERNAME,
                    user.name
                )
                editor.apply()

                when (activity) {
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }
                    is SettingsActivity -> {
                        activity.userDetailsSuccess(user)
                    }
                }
            }

            .addOnFailureListener { e ->

                when (activity) {
                    is LoginActivity -> {
                        activity.hideProgressDialog()
                    }
                    is SettingsActivity -> {
                        activity.hideProgressDialog()
                    }
                }
            }
    }

    fun uploadImageToCloudStorage(activity: Activity, imageFileURI: Uri?, imageType: String) {
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType + System.currentTimeMillis() + "."
                    + Constants.getFileExtension(activity, imageFileURI)
        )

        sRef.putFile(imageFileURI!!).addOnSuccessListener { taskSnapshot ->
            //Image upload is success
            Log.e(
                "Firebase Image URL",
                taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
            )

            //Get the downloadable url from the task snapshot
            taskSnapshot.metadata!!.reference!!.downloadUrl
                .addOnSuccessListener { uri ->
                    Log.e("Downloadable Image URL", uri.toString())
                    when (activity) {
                        is AddTrendingProductActivity -> {
                            activity.imageUploadSuccess(uri.toString())
                        }
                        is AddCategoryActivity -> {
                            activity.imageCategoryUploadSuccess(uri.toString())
                        }

                    }
                }
        }

            .addOnFailureListener { exception ->
                when (activity) {
                    is AddTrendingProductActivity -> {
                        activity.hideProgressDialog()
                    }
                    is AddCategoryActivity -> {
                        activity.hideProgressDialog()
                    }
                }
                // If have some error, It's printed in Log
                Log.e(
                    activity.javaClass.simpleName,
                    exception.message,
                    exception
                )
            }
    }

    fun uploadTrendingProductDetails(
        activity: AddTrendingProductActivity,
        productInfo: Product
    ) {
        mFireStore.collection(Constants.PRODUCTS)
            .document()
            .set(productInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.productUploadSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while uploading the product details.",
                    e
                )
            }
    }


    fun uploadCategoryDetails(
        activity: AddCategoryActivity, productCategoryInfo: ProductCategories
    ) {
        mFireStore.collection(Constants.CATEGORY)
            .document()
            .set(productCategoryInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.productCategoryUploadSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while uploading the category details.",
                    e
                )
            }
    }

    fun deleteTrendingProduct(fragment: ProductsTrendingFragment, productId: String) {
        mFireStore.collection(Constants.PRODUCTS)
            .document(productId)
            .delete()
            .addOnSuccessListener {
                fragment.productTrendingDeleteSuccess()

            }.addOnFailureListener { e ->
                fragment.hideProgressDialog()

                Log.e(
                    fragment.requireActivity().javaClass.simpleName,
                    "Error while deleting the product", e
                )
            }
    }

    fun getAllProductsList(fragment: ProductsTrendingFragment) {
        mFireStore.collection(Constants.PRODUCTS)
            .get()
            .addOnSuccessListener { document ->
                Log.e(fragment.javaClass.simpleName, document.documents.toString())

                val allProductsList: ArrayList<Product> = ArrayList()

                for (i in document.documents) {

                    val allProducts = i.toObject(Product::class.java)!!
                    allProducts.product_id = i.id
                    allProductsList.add(allProducts)
                }

                fragment.successAllProductList(allProductsList)

            }
            .addOnFailureListener { e ->
                fragment.hideProgressDialog()
                Log.e(fragment.javaClass.simpleName, "Error while getting All products", e)
            }
    }

    fun getProductsDetails(activity: ProductDetailActivity, productId: String) {
        mFireStore.collection(Constants.PRODUCTS)
            .document(productId)
            .get()
            .addOnSuccessListener { document ->
                Log.e(activity.javaClass.simpleName, document.toString())
                val product = document.toObject(Product::class.java)
                if (product != null) {
                    activity.productDetailsSuccess(product)
                }
            }
    }


    fun getMyOrdersList(fragment: OrdersFragment) {
        mFireStore.collection(Constants.ORDERS)
            .get()
            .addOnSuccessListener { document ->
                val list: ArrayList<Order> = ArrayList()

                for (i in document.documents) {
                    val orderItem = i.toObject(Order::class.java)!!
                    orderItem.id = i.id

                    list.add(orderItem)
                }
                fragment.populateOrdersListInUI(list)

            }.addOnFailureListener { e ->
                fragment.hideProgressDialog()
                Log.e(fragment.javaClass.simpleName, "Error while getting the orders list.", e)
            }
    }

    fun getAllCategoryProductsList(fragment: CategoryFragment) {
        mFireStore.collection(Constants.CATEGORY)
            .get()
            .addOnSuccessListener { document ->
                Log.e(fragment.javaClass.simpleName, document.documents.toString())

                val allCategoryProductsList: ArrayList<ProductCategories> = ArrayList()

                for (i in document.documents) {

                    val allCategoryProducts = i.toObject(ProductCategories::class.java)!!
                    allCategoryProducts.category_id = i.id
                    allCategoryProductsList.add(allCategoryProducts)
                }

                fragment.successAllCategoryProductList(allCategoryProductsList)

            }
            .addOnFailureListener { e ->
                fragment.hideProgressDialog()
                Log.e(fragment.javaClass.simpleName, "Error while getting All category", e)
            }
    }

}



