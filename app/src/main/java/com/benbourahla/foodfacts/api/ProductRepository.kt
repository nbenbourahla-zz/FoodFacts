package com.benbourahla.foodfacts.api

import com.benbourahla.foodfacts.database.ProductCache
import com.benbourahla.foodfacts.database.ProductDatabase
import com.benbourahla.foodfacts.database.entities.ProductDao
import com.benbourahla.foodfacts.model.ProductInformation
import io.reactivex.Single
import javax.inject.Inject

class ProductRepository @Inject constructor(val productService: ProductService,
                                            val productCache: ProductCache) {

    fun getProduct(barcode: String): Single<ProductInformation> {
        val productInCache =  productCache.contains(barcode)
        if (productInCache != null) {
            return Single.just(productInCache)
         } else {
             return productService.getProduct(barcode).doOnSuccess {
                 productCache.insert(it)}
        }
    }
}