package com.bonsai_software.icebonsai.domain

import com.bonsai_software.icebonsai.architecture.ShoppingDetailsRepository
import com.bonsai_software.icebonsai.presentation.models.DessertModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShoppingDetailsUseCase @Inject constructor(private val shoppingDetailsRepository: ShoppingDetailsRepository) {
    operator fun invoke(): Flow<List<DessertModel>> {
        return shoppingDetailsRepository.shoppingDetails
    }
}