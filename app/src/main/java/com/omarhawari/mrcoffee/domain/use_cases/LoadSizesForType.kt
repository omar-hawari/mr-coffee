package com.omarhawari.mrcoffee.domain.use_cases

import com.omarhawari.mrcoffee.data.data_source.local.CoffeeDatabase
import com.omarhawari.mrcoffee.domain.models.Size
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadSizesForType @Inject constructor(
    private val database: CoffeeDatabase
) {

    operator fun invoke(typeId: String): Flow<List<Size>> = flow {
        emit(database.dao.getSizesOfType(typeId).map { it.sizes }.flatten())
    }
}