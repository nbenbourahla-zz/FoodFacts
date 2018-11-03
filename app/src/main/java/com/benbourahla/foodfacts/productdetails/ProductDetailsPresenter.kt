package com.benbourahla.foodfacts.productdetails

import android.util.Log
import com.benbourahla.foodfacts.api.ProductRepository
import com.benbourahla.foodfacts.model.ProductInformation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.schedulers.IoScheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductDetailsPresenter @Inject constructor(val productRepository: ProductRepository) {

    private var screen: ProductDetailsScreen? = null
    private val compositeDisposable by lazy { CompositeDisposable() }

    fun bind(screen: ProductDetailsScreen) {
        this.screen = screen
    }

    fun onScreenStarted(productInformation: ProductInformation?) {
        checkNotNull(productInformation)
        checkNotNull(productInformation!!.product)

        val product = productInformation.product

        screen?.displayProductPicture(product.imageFrontSmallUrl)
        screen?.displayProductTitle(product.productName)
        screen?.displayProductBrand(product.brands)
        screen?.displayProductQuantity(product.quantity)
        if (product.nutriments.containsKey("energy_value")) {
            screen?.displayProductEnergie(product.nutriments["energy_value"])
        }
        if (product.Ingredients.isNotEmpty()) {
            screen?.displayProductIngredientsTitle()
            for (ingredient in product.Ingredients) {
                screen?.displayProductIngredient(ingredient.name)
            }
        }
    }

    fun onScreenDestroyed() {

    }

    fun unbind() {
        this.screen = null
    }


}