package com.omarhawari.mrcoffee.domain.models.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.omarhawari.mrcoffee.domain.models.Size
import com.omarhawari.mrcoffee.domain.models.Type


data class TypeWithSizes(
    @Embedded val type: Type,
    @Relation(
        parentColumn = "typeId",
        entityColumn = "sizeId",
        associateBy = Junction(TypeSizeCrossRef::class)
    ) val sizes: List<Size>
)