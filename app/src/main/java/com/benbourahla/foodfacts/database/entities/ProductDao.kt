package com.benbourahla.foodfacts.database.entities

import android.arch.persistence.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM ProductEntity")
    fun getAll(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productEntity: ProductEntity)

    @Update
    fun update(productEntity: ProductEntity)

    @Delete
    fun delete(vararg productEntities: ProductEntity)
}