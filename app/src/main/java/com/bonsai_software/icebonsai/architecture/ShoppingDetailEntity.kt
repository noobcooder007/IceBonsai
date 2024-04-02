package com.bonsai_software.icebonsai.architecture

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.Date

@Entity
data class ShoppingDetailEntity(
    @PrimaryKey
    val id: Int = System.currentTimeMillis().hashCode(),
    val shopping: Int,
    val dessert: String,
    val type: Int,
    val price: Int,
    val createdAt: String = LocalDateTime.now().toString(),
    var isActive: Boolean = true
)