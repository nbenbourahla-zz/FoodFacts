package com.benbourahla.foodfacts.api

import com.benbourahla.foodfacts.model.ProductInformation
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("api/v0/product/{productBarCode}.json")
    fun getProduct(@Path("productBarCode") productBarCode: String): Single<ProductInformation>
}