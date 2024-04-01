package com.bonsai_software.icebonsai.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bonsai_software.icebonsai.presentation.models.Dessert
import com.bonsai_software.icebonsai.presentation.models.DessertsType
import javax.inject.Inject

class DessertsViewModel @Inject constructor() : ViewModel() {
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog
    private var _total = MutableLiveData<Int>()
    val total: LiveData<Int> = _total
    private var _dessertCartList: MutableList<Dessert> = mutableListOf()
    private var _dessertCart = MutableLiveData<Map<DessertsType, List<Dessert>>>()
    val dessertCart: LiveData<Map<DessertsType, List<Dessert>>> = _dessertCart

    private fun updateDessertCart() {
        _dessertCart.postValue(_dessertCartList.groupBy { it.dessertType })
    }

    fun onDessertAdded(dessert: Dessert) {
        _dessertCartList.add(dessert)
        updateDessertCart()
        calculateCartPrice()
    }

    fun onDessertRemoved(dessert: Dessert) {
        _dessertCartList.remove(dessert)
        updateDessertCart()
        calculateCartPrice()
    }

    private fun calculateCartPrice() {
        _total.value = _dessertCartList.sumOf { it.price }
    }

    fun finishBuy() {}

    fun onConfirmDialog() {
        _showDialog.value = true
    }

    fun onDismissDialog() {
        _showDialog.value = false
    }
}