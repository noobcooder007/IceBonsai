package com.bonsai_software.icebonsai.presentation.models

enum class DessertsType {
    SINGLE_ICE_CREAM,
    SINGLE_CUP_ICE_CREAM,
    DOUBLE_ICE_CREAM,
    DOUBLE_CUP_ICE_CREAM,
    GOBLET_ICE_CREAM,
    SANDWICH_ICE_CREAM,
    VANILLA_ICE_POP,
    ESKIMO_ICE_POP
}

data class Dessert(
    val dessertId: Int = System.currentTimeMillis().hashCode(),
    val dessertName: String,
    val dessertType: DessertsType,
    val price: Int
)
