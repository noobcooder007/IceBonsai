package com.bonsai_software.icebonsai.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bonsai_software.icebonsai.architecture.ShoppingDao
import com.bonsai_software.icebonsai.architecture.ShoppingDetailEntity
import com.bonsai_software.icebonsai.architecture.ShoppingDetailsDao
import com.bonsai_software.icebonsai.architecture.ShoppingEntity

@Database(entities = [ShoppingEntity::class, ShoppingDetailEntity::class], version = 1, exportSchema = false)
abstract class IceBonsaiDatabase: RoomDatabase() {
    // DAO
    abstract fun shoppingDetailsDao(): ShoppingDetailsDao
    abstract fun shoppingDao(): ShoppingDao
}