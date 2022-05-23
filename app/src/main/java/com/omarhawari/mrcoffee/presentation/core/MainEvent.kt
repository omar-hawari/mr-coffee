package com.omarhawari.mrcoffee.presentation.core

sealed class MainEvent {
    data class TypeSelected(val typeIndex: Int) : MainEvent()
    data class SizeSelected(val sizeIndex: Int) : MainEvent()
    data class SubSelectionSelected(val extraId: String, val subSelectionIndex: Int) : MainEvent()

    /**
     * Navigation events:
    * */
    object TypesToSizes : MainEvent()
    object SizesToExtras : MainEvent()
    object ExtrasToCheckout : MainEvent()
    object CheckoutToTypes : MainEvent()
    object CheckoutToSizes : MainEvent()
    object CheckoutToExtras : MainEvent()
}

