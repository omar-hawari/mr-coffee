package com.omarhawari.mrcoffee.domain.use_cases

import com.omarhawari.mrcoffee.core.Resource
import com.omarhawari.mrcoffee.data.data_source.remote.dto.BaseResponse
import com.omarhawari.mrcoffee.data.data_source.remote.dto.toType
import com.omarhawari.mrcoffee.domain.models.Type
import com.omarhawari.mrcoffee.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllUseCase @Inject constructor(
    private val repository: CoffeeRepository
) {

    operator fun invoke(machineId: String): Flow<Resource<BaseResponse>> {
        return flow {
            try {
                emit(Resource.Loading<BaseResponse>())
                val response = repository.getAll(machineId)
                emit(Resource.Success<BaseResponse>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<BaseResponse>(
                        e.localizedMessage ?: "Unexpected error occurred."
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<BaseResponse>("You're offline."))
            }
        }
    }
}