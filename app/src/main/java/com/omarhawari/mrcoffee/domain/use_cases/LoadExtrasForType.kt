package com.omarhawari.mrcoffee.domain.use_cases

import com.omarhawari.mrcoffee.data.data_source.local.CoffeeDatabase
import com.omarhawari.mrcoffee.domain.models.Extra
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadExtrasForType @Inject constructor(
    private val database: CoffeeDatabase
) {

    operator fun invoke(typeId: String): Flow<List<Extra>> = flow {
        emit(database.dao.getExtrasOfType(typeId).map { it.extras }.flatten())
    }
}