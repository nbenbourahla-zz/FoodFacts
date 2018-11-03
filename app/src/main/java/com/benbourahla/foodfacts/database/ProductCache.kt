package com.benbourahla.foodfacts.database

import android.arch.persistence.room.Room
import android.content.Context
import com.benbourahla.foodfacts.database.entities.Product
import com.benbourahla.foodfacts.database.entities.ProductDao
import com.benbourahla.foodfacts.model.Ingredient
import com.benbourahla.foodfacts.model.ProductInformation

class ProductCache(val productDao: ProductDao) {

    fun insert(productInformation: ProductInformation) {
        val product = Product(
                code = productInformation.code,
                statusVerbose = productInformation.status,
                id = productInformation.product.id,
                ingredients = getIngredients(productInformation.product.Ingredients),
                brands = productInformation.product.brands,
                productName = productInformation.product.productName,
                imageFrontSmallUrl = productInformation.product.imageFrontSmallUrl,
                quantity = productInformation.product.quantity,
                energy = if (productInformation.product.nutriments.containsKey("energy_value")) null else productInformation.product.nutriments.get("energy_value"),
                link =  productInformation.product.link

        )

        productDao.insert(product)
    }

    private fun getIngredients(ingredients: List<Ingredient>): String {
        val ingredientsStr = StringBuilder()
        for (ingredient in ingredients) {
            ingredientsStr.append("\\u2713" + ingredient.name + "\n")
        }
        return ingredientsStr.toString()
    }

}