package com.benbourahla.foodfacts.productdetails

import com.benbourahla.foodfacts.ENERGY_VALUE
import com.benbourahla.foodfacts.api.ProductRepository
import com.benbourahla.foodfacts.model.ProductInformation
import io.reactivex.disposables.CompositeDisposable
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

        val product = productInformation.product!!

        screen?.displayProductPicture(product.imageFrontSmallUrl)
        screen?.displayProductTitle(product.productName)
        screen?.displayProductBrand(product.brands)
        screen?.displayProductQuantity(product.quantity)
        if (product.nutriments.containsKey(ENERGY_VALUE)) {
            screen?.displayProductEnergy(product.nutriments[ENERGY_VALUE])
        }
        if (product.ingredients.isNotEmpty()) {
            screen?.displayProductIngredientsTitle()
            for (ingredient in product.ingredients) {
                screen?.displayProductIngredient(ingredient.name)
            }
        }
    }

    fun onScreenDestroyed() {
        compositeDisposable.clear()
    }

    fun unbind() {
        this.screen = null
    }


}