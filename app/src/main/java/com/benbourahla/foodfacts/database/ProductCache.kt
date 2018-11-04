package com.benbourahla.foodfacts.database

import android.annotation.SuppressLint
import com.benbourahla.foodfacts.ENERGY_VALUE
import com.benbourahla.foodfacts.PRODUCT_FOUND
import com.benbourahla.foodfacts.PRODUCT_NOT_FOUND
import com.benbourahla.foodfacts.database.entities.ProductDao
import com.benbourahla.foodfacts.database.entities.ProductEntity
import com.benbourahla.foodfacts.model.Ingredient
import com.benbourahla.foodfacts.model.ProductInformation
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ProductCache(private val productDao: ProductDao) {

    fun insert(productInformation: ProductInformation) {
        checkNotNull(productInformation.product)
        val product = productInformation.product!!
        val productEntity = ProductEntity(
                code = productInformation.code,
                statusVerbose = productInformation.status,
                id = product.id,
                ingredients = getIngredients(product.ingredients),
                brands = product.brands,
                productName = product.productName,
                imageFrontSmallUrl = product.imageFrontSmallUrl,
                quantity = product.quantity,
                energy = if (product.nutriments.containsKey(ENERGY_VALUE)) null else product.nutriments[ENERGY_VALUE],
                link = product.link

        )

        productDao.insert(productEntity)
    }

    @SuppressLint("CheckResult")
    fun contains(barcode: String): ProductInformation? {
        return Observable.fromCallable { find(barcode) }.subscribeOn(Schedulers.io()).blockingFirst()
    }

    @SuppressLint("CheckResult")
    fun getAll(): List<ProductInformation> {
        return Observable.fromCallable { findAll() }.subscribeOn(Schedulers.io()).blockingFirst()
    }

    private fun findAll(): List<ProductInformation> {
        val list = mutableListOf<ProductInformation>()
        for (product in productDao.getAll()) {
            list.add(createProductInformation(product))
        }
        return list
    }

    private fun find(barcode: String): ProductInformation {
        for (product in productDao.getAll()) {
            if (product.code == barcode) {
                return createProductInformation(product)
            }
        }
        return ProductInformation(code = "", product = null, status = PRODUCT_NOT_FOUND)
    }

    private fun createProductInformation(productEntity: ProductEntity): ProductInformation {
        val ingredients = arrayListOf<Ingredient>()
        for (ingredient in productEntity.ingredients.split(",")) {
            ingredients.add(Ingredient(name = ingredient))
        }
        val prod = com.benbourahla.foodfacts.model.Product(
                id = productEntity.id,
                brands = productEntity.brands,
                productName = productEntity.productName,
                imageFrontSmallUrl = productEntity.imageFrontSmallUrl,
                quantity = productEntity.quantity,
                link = productEntity.link,
                nutriments = mapOf((ENERGY_VALUE to productEntity.energy) as Pair<String, String>),
                ingredients = ingredients
        )
        return ProductInformation(code = productEntity.code,
                status = PRODUCT_FOUND,
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