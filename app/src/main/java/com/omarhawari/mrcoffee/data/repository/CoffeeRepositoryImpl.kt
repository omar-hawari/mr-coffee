package com.omarhawari.mrcoffee.data.repository

import com.omarhawari.mrcoffee.data.data_source.remote.CoffeeAPI
import com.omarhawari.mrcoffee.data.data_source.remote.dto.BaseResponse
import com.omarhawari.mrcoffee.domain.repository.CoffeeRepository
import javax.inject.Inject

class CoffeeRepositoryImpl @Inject constructor(
    private val api: CoffeeAPI
) : CoffeeRepository {

    override suspend fun getAll(machineId: String): BaseResponse {
        return api.getAll(machineId)
    }
}