package com.omarhawari.mrcoffee.domain.use_cases

import com.omarhawari.mrcoffee.data.data_source.local.CoffeeDatabase
import com.omarhawari.mrcoffee.data.data_source.remote.dto.*
import com.omarhawari.mrcoffee.domain.models.relations.TypeExtraCrossRef
import com.omarhawari.mrcoffee.domain.models.relations.TypeSizeCrossRef
import javax.inject.Inject

class SaveAllUseCase @Inject constructor(
    private val database: CoffeeDatabase
) {

    suspend operator fun invoke(baseResponse: BaseResponse) {
        baseResponse.types.forEach { type ->
            database.dao.insertType(type.toType())
            type.sizes.forEach {
                database.dao.insertTypeSizeCrossRef(TypeSizeCrossRef(it, type._id))
            }
            type.extras.forEach {
                database.dao.insertTypeExtraCrossRef(TypeExtraCrossRef(it, type._id))
            }
        }
        baseResponse.sizes.forEach { size ->
            database.dao.insertSize(size.toSize())
        }
        baseResponse.extras.forEach { extra ->
            database.dao.insertExtra(extra.toExtra())
            extra.subselections.forEach {

                database.dao.insertSubSelection(
                    it.toSubSelection().apply { this.extraId = extra._id })
            }
        }
    }
}