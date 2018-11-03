package com.benbourahla.foodfacts.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Product")
data class Product(
        @PrimaryKey(autoGenerate = false) val code: String,
        @ColumnInfo(name = "status") val statusVerbose:String,
        @ColumnInfo(name = "id") val id: String,
        @ColumnInfo(name = "ingredients") val ingredients: String,
        @ColumnInfo(name = "brands") val brands: String,
        @ColumnInfo(name = "name") val productName: String,
        @ColumnInfo(name = "img_url") val imageFrontSmallUrl: String,
        @ColumnInfo(name = "quantity") val quantity: String,
        @ColumnInfo(name = "energy") val energy: String?,
        @ColumnInfo(name = "link") val link: String?
)