package com.benbourahla.foodfacts.productdetails

import com.benbourahla.foodfacts.api.ApiModule
import com.benbourahla.foodfacts.database.CacheModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApiModule::class), (CacheModule::class)])
interface ProductDetailsComponent {
    fun inject(activity: ProductDetailsActivity)
}