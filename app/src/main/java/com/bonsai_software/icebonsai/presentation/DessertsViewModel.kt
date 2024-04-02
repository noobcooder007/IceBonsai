package com.bonsai_software.icebonsai.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai_software.icebonsai.domain.AddShoppingUseCase
import com.bonsai_software.icebonsai.domain.GetShoppingUseCase
import com.bonsai_software.icebonsai.presentation.DessertsUiState.Success
import com.bonsai_software.icebonsai.presentation.models.DessertModel
import com.bonsai_software.icebonsai.presentation.models.DessertsType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DessertsViewModel @Inject constructor(
    private val addShoppingUseCase: AddShoppingUseCase,
    getShoppingUseCase: GetShoppingUseCase
) : ViewModel() {
    val uiState: StateFlow<DessertsUiState> = getShoppingUseCase().map(::Success)
        .catch { DessertsUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DessertsUiState.Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog
    private var _total = MutableLiveData<Int>()
    val total: LiveData<Int> = _total
    private var _dessertCartList: MutableList<DessertModel> = mutableListOf()
    private var _dessertCart = MutableLiveData<Map<DessertsType, List<DessertModel>>>()
    val dessertCart: LiveData<Map<DessertsType, List<DessertModel>>> = _dessertCart

    private fun updateDessertCart() {
        _dessertCart.postValue(_dessertCartList.groupBy { it.dessertType })
        calculateCartPrice()
    }

    fun onDessertAdded(dessert: DessertModel) {
        _dessertCartList.add(dessert)
        updateDessertCart()
    }

    fun onDessertRemoved(dessert: DessertModel) {
        _dessertCartList.remove(dessert)
        updateDessertCart()
    }

    private fun calculateCartPrice() {
        _total.value = _dessertCartList.sumOf { it.price }
    }

    fun finishBuy() {
        viewModelScope.launch {
            addShoppingUseCase(_dessertCartList)
        }
        _dessertCartList.clear()
        updateDessertCart()
        _showDialog.value = false
    }

    fun onConfirmDialog() {
        _showDialog.value = true
    }

    fun onDismissDialog() {
        _showDialog.value = false
    }
}