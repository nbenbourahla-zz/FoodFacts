package com.benbourahla.foodfacts.searchproduct

import com.benbourahla.foodfacts.api.ApiModule
import com.benbourahla.foodfacts.database.CacheModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApiModule::class), (CacheModule::class)])
interface SearchProductComponent {
    fun inject(activity: SearchProductActivity)
}