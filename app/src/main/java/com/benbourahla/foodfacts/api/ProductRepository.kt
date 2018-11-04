package com.benbourahla.foodfacts.api

import com.benbourahla.foodfacts.PRODUCT_FOUND
import com.benbourahla.foodfacts.PRODUCT_NOT_FOUND
import com.benbourahla.foodfacts.database.ProductCache
import com.benbourahla.foodfacts.model.ProductInformation
import io.reactivex.Single
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productService: ProductService,
                                            private val productCache: ProductCache) {

    fun getProduct(barcode: String): Single<ProductInformation> {
        val productInCache =  productCache.contains(barcode)
        return if (productInCache != null && productInCache.status != PRODUCT_NOT_FOUND) {
            Single.just(productInCache)
        } else {
            productService.getProduct(barcode).doOnSuccess {
                if (it.status == PRODUCT_FOUND) {
                    productCache.insert(it)
                }
            }
        }
    }

    fun getProductHistoryList(): List<ProductInformation> {
      return productCache.getAll()
    }
}