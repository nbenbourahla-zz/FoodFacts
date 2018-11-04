package com.benbourahla.foodfacts.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(@SerializedName("text") val name: String,
                      @SerializedName("id") val id: String = "",
                      @SerializedName("rank") val rank: Int = 0) : Parcelable