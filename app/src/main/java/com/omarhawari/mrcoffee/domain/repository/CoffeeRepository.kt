package com.omarhawari.mrcoffee.domain.repository

import com.omarhawari.mrcoffee.data.data_source.remote.dto.BaseResponse

interface CoffeeRepository {

    suspend fun getAll(machineId: String): BaseResponse

}