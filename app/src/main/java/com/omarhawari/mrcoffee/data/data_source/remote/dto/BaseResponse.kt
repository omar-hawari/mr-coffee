package com.omarhawari.mrcoffee.data.data_source.remote.dto

data class BaseResponse(
    val _id: String,
    val extras: List<ExtraDto>,
    val sizes: List<SizeDto>,
    val types: List<TypeDto>
)

fun BaseResponse.toProperResponse() {
    return
}