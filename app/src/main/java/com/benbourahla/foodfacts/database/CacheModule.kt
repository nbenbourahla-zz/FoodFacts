package com.benbourahla.foodfacts.database

import android.content.Context
import com.benbourahla.foodfacts.database.entities.ProductDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideDao(): ProductDao {
        return ProductDatabase.getInstance(context)?.productDataDao()!!
    }

    @Provides
    @Singleton
    fun provideCache(productDao: ProductDao): ProductCache {
        return ProductCache(productDao)
    }
}

