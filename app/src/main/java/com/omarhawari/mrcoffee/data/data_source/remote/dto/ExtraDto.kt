package com.omarhawari.mrcoffee.data.data_source.remote.dto

import com.omarhawari.mrcoffee.domain.models.Extra

data class ExtraDto(
    val _id: String,
    val name: String,
    val subselections: List<SubSelectionDto>
)

fun ExtraDto.toExtra(): Extra {
    return Extra(
        _id, name
    )
}