package com.bonsai_software.icebonsai.domain

import com.bonsai_software.icebonsai.architecture.ShoppingRepository
import com.bonsai_software.icebonsai.presentation.models.ShoppingModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShoppingUseCase @Inject constructor(private val shoppingRepository: ShoppingRepository) {
    operator fun invoke(): Flow<List<ShoppingModel>> {
        return shoppingRepository.shopping
    }
}