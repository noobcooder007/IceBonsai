package com.bonsai_software.icebonsai.architecture

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDetailsDao {

    @Query("SELECT * FROM ShoppingDetailEntity")
    fun getShoppingDetails(): Flow<List<ShoppingDetailEntity>>

    @Insert
    suspend fun finishBuy(items: List<ShoppingDetailEntity>)
}