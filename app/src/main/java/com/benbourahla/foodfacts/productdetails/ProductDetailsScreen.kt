package com.benbourahla.foodfacts.productdetails

interface ProductDetailsScreen {
    fun displayProductPicture(imgUrl: String)
    fun displayProductTitle(productName: String)
    fun displayProductBrand(brand: String)
    fun displayProductQuantity(quantity: String)
    fun displayProductEnergy(energyValue: String?)
    fun displayProductIngredientsTitle()
    fun displayProductIngredient(name: String)
}