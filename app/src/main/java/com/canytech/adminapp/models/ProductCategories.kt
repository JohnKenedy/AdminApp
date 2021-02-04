package com.canytech.adminapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductCategories(
    val category_title: String = "",
    val img_category: String = "",
    var category_id: String = ""
    ): Parcelable



