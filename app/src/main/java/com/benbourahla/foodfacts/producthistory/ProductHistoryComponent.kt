package com.benbourahla.foodfacts.producthistory

import com.benbourahla.foodfacts.api.ApiModule
import com.benbourahla.foodfacts.database.CacheModule
import com.benbourahla.foodfacts.productdetails.ProductDetailsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApiModule::class), (CacheModule::class)])
interface ProductHistoryComponent {
    fun inject(activity: ProductHistoryActivity)
}