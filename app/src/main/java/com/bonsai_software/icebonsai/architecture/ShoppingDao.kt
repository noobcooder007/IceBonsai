package com.bonsai_software.icebonsai.architecture

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {
    @Query("SELECT * FROM ShoppingEntity")
    fun getShopping(): Flow<List<ShoppingEntity>>

    @Insert
    suspend fun insertShop(shop: ShoppingEntity)
}