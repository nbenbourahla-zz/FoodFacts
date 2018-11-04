package com.benbourahla.foodfacts.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(@SerializedName("id") val id: String,
                   @SerializedName("brands") val brands: String,
                   @SerializedName("ingredients") val ingredients: List<Ingredient>,
                   @SerializedName("product_name") val productName: String,
                   @SerializedName("image_front_small_url") val imageFrontSmallUrl: String,
                   @SerializedName("quantity") val quantity: String,
                   @SerializedName("link") val link: String?,
                   @SerializedName("nutriments") val nutriments: Map<String, String>
    ) : Parcelable