package com.bonsai_software.icebonsai.domain

import com.bonsai_software.icebonsai.architecture.ShoppingDetailsRepository
import com.bonsai_software.icebonsai.presentation.models.DessertModel
import javax.inject.Inject

class AddShoppingDetailsUseCase @Inject constructor(private val shoppingDetailsRepository: ShoppingDetailsRepository) {
    suspend operator fun invoke(shopping: List<DessertModel>, shoppingId: Int) {
        shoppingDetailsRepository.addShoppingDetails(shopping, shoppingId)
    }
}