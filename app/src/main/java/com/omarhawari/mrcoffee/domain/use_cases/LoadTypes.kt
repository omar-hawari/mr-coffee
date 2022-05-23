package com.omarhawari.mrcoffee.domain.use_cases

import com.omarhawari.mrcoffee.data.data_source.local.CoffeeDatabase
import com.omarhawari.mrcoffee.domain.models.Type
import javax.inject.Inject

class LoadTypes @Inject constructor(
    private val database: CoffeeDatabase
) {

    suspend operator fun invoke(): List<Type> = database.dao.getTypes()
}