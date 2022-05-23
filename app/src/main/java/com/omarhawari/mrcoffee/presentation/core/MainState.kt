package com.omarhawari.mrcoffee.presentation.core

import com.omarhawari.mrcoffee.domain.models.Size
import com.omarhawari.mrcoffee.domain.models.Type
import com.omarhawari.mrcoffee.domain.models.relations.ExtraWithSubSelectionsSelected

class MainState(
    val isLoading: Boolean = false,
    val selectedType: Int = -1,
    val selectedSize: Int = -1,
    val types: List<Type> = emptyList(),
    val sizes: List<Size> = emptyList(),
    val extras: List<ExtraWithSubSelectionsSelected> = emptyList(),
    val error: String = "",
) {

    fun copyWith(
        isLoading: Boolean = this.isLoading,
        selectedType: Int = this.selectedType,
        selectedSize: Int = this.selectedSize,
        types: List<Type> = this.types,
        sizes: List<Size> = this.sizes,
        extras: List<ExtraWithSubSelectionsSelected> = this.extras,
        error: String = this.error,
    ): MainState {
        return MainState(
            isLoading = isLoading,
            selectedType = selectedType,
            selectedSize = selectedSize,
            types = types,
            sizes = sizes,
            extras = extras,
            error = error,
        )
    }

    override fun toString(): String {
        return "MainState(isLoading=$isLoading, selectedType=$selectedType, selectedSize=$selectedSize, types=$types, sizes=$sizes, extras=$extras, error='$error')"
    }


}