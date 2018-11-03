package com.benbourahla.foodfacts.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductInformation(
        @SerializedName("code") val code: String,
        @SerializedName("status_verbose") val status:String,
        @SerializedName("product") val product:Product
) : Parcelable