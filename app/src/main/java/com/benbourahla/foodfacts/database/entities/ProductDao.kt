package com.benbourahla.foodfacts.database.entities

import android.arch.persistence.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product")
    fun getAll(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Delete
    fun delete(vararg products: Product)
}