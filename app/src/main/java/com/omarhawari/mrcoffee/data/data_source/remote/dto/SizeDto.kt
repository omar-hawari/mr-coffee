package com.omarhawari.mrcoffee.data.data_source.remote.dto

import com.omarhawari.mrcoffee.domain.models.Size

data class SizeDto(
    val __v: Int,
    val _id: String,
    val name: String
)

fun SizeDto.toSize(): Size {
    return Size(
        sizeId = _id,
        name = name
    )
}