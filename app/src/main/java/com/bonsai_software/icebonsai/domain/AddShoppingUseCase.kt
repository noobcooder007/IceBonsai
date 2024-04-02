package com.bonsai_software.icebonsai.domain

import com.bonsai_software.icebonsai.architecture.ShoppingRepository
import com.bonsai_software.icebonsai.presentation.models.DessertModel
import javax.inject.Inject

class AddShoppingUseCase @Inject constructor(private val shoppingRepository: ShoppingRepository) {
    suspend operator fun invoke(shopping: List<DessertModel>) {
        shoppingRepository.addShop(shopping)
    }
}