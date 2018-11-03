package com.benbourahla.foodfacts.searchproduct

import com.benbourahla.foodfacts.model.ProductInformation

interface SearchProductScreen {
    fun goToNextScreen(it: ProductInformation?)
    fun displayInputError()
    fun displayError(error: Throwable?)
}