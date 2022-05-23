package com.omarhawari.mrcoffee.domain.models.relations

import androidx.room.Entity

/**
 * Many (Type) to Many (Extra) Relation
 *
 * */
@Entity(primaryKeys = ["extraId", "typeId"])
data class TypeExtraCrossRef(
    val extraId: String,
    val typeId: String
)