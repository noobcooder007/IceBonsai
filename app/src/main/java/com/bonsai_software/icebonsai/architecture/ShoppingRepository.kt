package com.bonsai_software.icebonsai.architecture

import com.bonsai_software.icebonsai.presentation.models.DessertModel
import com.bonsai_software.icebonsai.presentation.models.ShoppingModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingRepository @Inject constructor(private val shoppingDao: ShoppingDao) {
    val shopping: Flow<List<ShoppingModel>> = shoppingDao.getShopping().map { items ->
        items.map {
            ShoppingModel(it.id, it.total, it.createdAt)
        }
    }

    suspend fun addShop(desserts: List<DessertModel>) {
        shoppingDao.insertShop(shop = ShoppingEntity(total = desserts.sumOf { it.price }))
    }
}