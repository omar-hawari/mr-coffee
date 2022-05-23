package com.omarhawari.mrcoffee.domain.models.relations

import androidx.room.Entity

/**
 * Many (Type) to Many (Size) Relation
 *
 * */
@Entity(primaryKeys = ["sizeId", "typeId"])
data class TypeSizeCrossRef(
    val sizeId: String,
    val typeId: String
)