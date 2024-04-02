package com.bonsai_software.icebonsai.architecture

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class ShoppingEntity(
    @PrimaryKey
    val id: Int = System.currentTimeMillis().hashCode(),
    var total: Int,
    var createdAt: String = LocalDateTime.now().toString(),
    var isActive: Boolean = true
)
