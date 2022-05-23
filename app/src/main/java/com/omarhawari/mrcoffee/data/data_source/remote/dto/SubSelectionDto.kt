package com.omarhawari.mrcoffee.data.data_source.remote.dto

import com.omarhawari.mrcoffee.domain.models.SubSelection

data class SubSelectionDto(
    val _id: String,
    val name: String
)

fun SubSelectionDto.toSubSelection(): SubSelection {
    return SubSelection(
        subSelectionId = _id,
        name = name,
    )
}