package com.benbourahla.foodfacts.database

import android.annotation.SuppressLint
import com.benbourahla.foodfacts.database.entities.Product
import com.benbourahla.foodfacts.database.entities.ProductDao
import com.benbourahla.foodfacts.model.Ingredient
import com.benbourahla.foodfacts.model.ProductInformation
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.Future

class ProductCache(val productDao: ProductDao) {

    fun insert(productInformation: ProductInformation) {
        val product = Product(
                code = productInformation.code,
                statusVerbose = productInformation.status,
                id = productInformation.product.id,
                ingredients = getIngredients(productInformation.product.ingredients),
                brands = productInformation.product.brands,
                productName = productInformation.product.productName,
                imageFrontSmallUrl = productInformation.product.imageFrontSmallUrl,
                quantity = productInformation.product.quantity,
                energy = if (productInformation.product.nutriments.containsKey("energy_value")) null else productInformation.product.nutriments.get("energy_value"),
                link = productInformation.product.link

        )

        productDao.insert(product)
    }

    @SuppressLint("CheckResult")
    fun contains(barcode: String): ProductInformation? {
        //val bs = BehaviorSubject.create<ProductInformation>()

        val productInformation = Observable.fromCallable { find(barcode) }.subscribeOn(Schedulers.io()).blockingFirst()

        return productInformation
    }

    private fun find(barcode: String): ProductInformation? {
        for (product in productDao.getAll()) {
            if (product.code == barcode) {
                return createProductInformation(product)
            }
        }
        return null
    }

    private fun createProductInformation(product: Product): ProductInformation {
        val ingredients = arrayListOf<Ingredient>()
        for (ingredient in product.ingredients.split(",")) {
            ingredients.add(Ingredient(name = ingredient))
        }
        val prod = com.benbourahla.foodfacts.model.Product(
                id = product.id,
                brands = product.brands,
                productName = product.productName,
                imageFrontSmallUrl = product.imageFrontSmallUrl,
                quantity = product.quantity,
                link = product.link,
                nutriments = mapOf(("energy" to product.energy) as Pair<String, String>),
                ingredients = ingredients
        )
        return ProductInformation(code = product.code,
                status = product.statusVerbose,
                product = prod)
    }

    private fun getIngredients(ingredients: List<Ingredient>): String {
        val ingredientsStr = StringBuilder()
        for (ingredient in ingredients) {
            ingredientsStr.append(ingredient.name).append(",")
        }
        return ingredientsStr.toString()
    }

}