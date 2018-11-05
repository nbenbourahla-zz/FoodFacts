package com.benbourahla.foodfacts.searchproduct

import android.support.design.widget.Snackbar
import android.util.Log
import com.benbourahla.foodfacts.PRODUCT_NOT_FOUND
import com.benbourahla.foodfacts.R
import com.benbourahla.foodfacts.api.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.schedulers.IoScheduler
import io.reactivex.schedulers.Schedulers
import java.lang.IllegalStateException
import javax.inject.Inject

class SearchProductPresenter @Inject constructor(private val productRepository: ProductRepository) {

    private var screen: SearchProductScreen? = null
    private val compositeDisposable by lazy { CompositeDisposable() }

    fun bind(screen: SearchProductScreen) {
        this.screen = screen
    }

    fun onSearchButtonClicked(codeBarText: String?) {
        if (codeBarText.isNullOrEmpty()) {
            screen?.displayInputError()
        } else {
            findProduct(codeBarText!!)
        }
    }

    fun findProduct(barcode: String) {
        compositeDisposable.add(productRepository.getProduct(barcode).observeOn(
                AndroidSchedulers.mainThread()
        ).subscribeOn(Schedulers.io()).subscribe(
                {
                    if (it.status == PRODUCT_NOT_FOUND) {
                        screen?.displayError(IllegalStateException("Product Not Found"))
                    } else {
                        screen?.goToNextScreen(it)
                    }
                },
                {
                    screen?.displayError(it)
                }
        ))
    }

    fun unbind() {
        this.screen = null
        this.compositeDisposable.clear()
    }




}