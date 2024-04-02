package com.bonsai_software.icebonsai.architecture

import com.bonsai_software.icebonsai.presentation.models.DessertModel
import com.bonsai_software.icebonsai.presentation.models.DessertsType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingDetailsRepository @Inject constructor(private  val shoppingDetailsDao: ShoppingDetailsDao) {
    val shoppingDetails: Flow<List<DessertModel>> = shoppingDetailsDao.getShoppingDetails().map { items -> items.map {
        when (it.type) {
            1 -> DessertModel(it.id, it.dessert, DessertsType.SINGLE_ICE_CREAM, it.price)
            2 -> DessertModel(it.id, it.dessert, DessertsType.SINGLE_CUP_ICE_CREAM, it.price)
            3 -> DessertModel(it.id, it.dessert, DessertsType.DOUBLE_ICE_CREAM, it.price)
            4 -> DessertModel(it.id, it.dessert, DessertsType.DOUBLE_CUP_ICE_CREAM, it.price)
            5 -> DessertModel(it.id, it.dessert, DessertsType.GOBLET_ICE_CREAM, it.price)
            6 -> DessertModel(it.id, it.dessert, DessertsType.SANDWICH_ICE_CREAM, it.price)
            7 -> DessertModel(it.id, it.dessert, DessertsType.VANILLA_ICE_POP, it.price)
            8 -> DessertModel(it.id, it.dessert, DessertsType.ESKIMO_ICE_POP, it.price)
            else -> { throw Error() }
        }
    } }

    suspend fun addShoppingDetails(desserts: List<DessertModel>, shoppingId: Int) {
        shoppingDetailsDao.finishBuy(items = desserts.map {
            when(it.dessertType) {
                DessertsType.SINGLE_ICE_CREAM -> ShoppingDetailEntity(it.dessertId, shoppingId, it.dessertName, 1, it.price)
                DessertsType.SINGLE_CUP_ICE_CREAM -> ShoppingDetailEntity(it.dessertId, shoppingId, it.dessertName, 2, it.price)
                DessertsType.DOUBLE_ICE_CREAM -> ShoppingDetailEntity(it.dessertId, shoppingId, it.dessertName, 3, it.price)
                DessertsType.DOUBLE_CUP_ICE_CREAM -> ShoppingDetailEntity(it.dessertId, shoppingId, it.dessertName, 4, it.price)
                DessertsType.GOBLET_ICE_CREAM -> ShoppingDetailEntity(it.dessertId, shoppingId, it.dessertName, 5, it.price)
                DessertsType.SANDWICH_ICE_CREAM -> ShoppingDetailEntity(it.dessertId, shoppingId, it.dessertName, 6, it.price)
                DessertsType.VANILLA_ICE_POP -> ShoppingDetailEntity(it.dessertId, shoppingId, it.dessertName, 7, it.price)
                DessertsType.ESKIMO_ICE_POP -> ShoppingDetailEntity(it.dessertId, shoppingId, it.dessertName, 8, it.price)
            }
        })
    }
}