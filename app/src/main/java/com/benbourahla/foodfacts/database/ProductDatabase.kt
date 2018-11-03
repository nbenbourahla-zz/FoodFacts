package com.benbourahla.foodfacts.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.benbourahla.foodfacts.database.entities.Product
import com.benbourahla.foodfacts.database.entities.ProductDao

@Database(entities = arrayOf(Product::class), version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun weatherDataDao(): ProductDao

    companion object {
        private var INSTANCE: ProductDatabase? = null

        fun getInstance(context: Context): ProductDatabase? {
            if (INSTANCE == null) {
                synchronized(ProductDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            ProductDatabase::class.java, "product.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}