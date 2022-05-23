package com.omarhawari.mrcoffee.data.data_source.remote

import com.omarhawari.mrcoffee.data.data_source.remote.dto.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CoffeeAPI {

    @GET("coffee-machine/{machineId}")
    suspend fun getAll(@Path("machineId") machineId: String): BaseResponse

}