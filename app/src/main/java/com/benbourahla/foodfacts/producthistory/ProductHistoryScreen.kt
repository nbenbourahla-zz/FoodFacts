package com.benbourahla.foodfacts.producthistory

import android.widget.ImageView
import com.benbourahla.foodfacts.model.ProductInformation

interface ProductHistoryScreen {
    fun displayProductHistory(productHistoryList: List<ProductInformation>)
    fun displayProductPicture(url: String?, productPicture: ImageView?)
    fun displayProductDetails(product: ProductInformation)
    fun displayEmptyState()
    fun displayTitle()
}