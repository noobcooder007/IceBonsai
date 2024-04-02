package com.bonsai_software.icebonsai.presentation

import com.bonsai_software.icebonsai.presentation.models.ShoppingModel

sealed interface DessertsUiState {
    data object Loading: DessertsUiState
    data class Error(val throwable: Throwable): DessertsUiState
    data class Success(val shopping: List<ShoppingModel>): DessertsUiState
}