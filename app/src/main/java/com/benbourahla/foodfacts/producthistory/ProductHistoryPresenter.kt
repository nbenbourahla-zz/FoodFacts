package com.benbourahla.foodfacts.producthistory

import com.benbourahla.foodfacts.api.ProductRepository
import com.benbourahla.foodfacts.model.ProductInformation
import com.benbourahla.foodfacts.productdetails.ProductDetailsScreen
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ProductHistoryPresenter @Inject constructor(val productRepository: ProductRepository) {

    private var screen: ProductHistoryScreen? = null

    fun bind(screen: ProductHistoryScreen) {
        this.screen = screen
    }

    fun onScreenStarted() {
        val historyList = productRepository.getProductHistoryList()
        if (historyList.isEmpty()) {
            screen?.displayEmptyState()
        } else {
            screen?.displayTitle()
            screen?.displayProductHistory(historyList)
        }
    }

    fun unbind() {
        this.screen = null
    }


}