package com.omarhawari.mrcoffee.domain.use_cases

import com.omarhawari.mrcoffee.data.data_source.local.CoffeeDatabase
import com.omarhawari.mrcoffee.domain.models.Extra
import com.omarhawari.mrcoffee.domain.models.SubSelection
import com.omarhawari.mrcoffee.domain.models.relations.ExtraWithSubSelections
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadSubSelectionsForExtra @Inject constructor(
    private val database: CoffeeDatabase
) {

    suspend operator fun invoke(typeId: String): ExtraWithSubSelections  {
        return database.dao.getSubSelectionsOfExtra(typeId)[0]
    }
}