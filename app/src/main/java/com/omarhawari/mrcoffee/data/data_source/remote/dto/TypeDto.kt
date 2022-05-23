package com.omarhawari.mrcoffee.data.data_source.remote.dto

import com.omarhawari.mrcoffee.domain.models.Type

data class TypeDto(
    val _id: String,
    val extras: List<String>,
    val name: String,
    val sizes: List<String>
)

fun TypeDto.toType(): Type {
    return Type(
        typeId = _id,
        name = name
    )
}