package com.omarhawari.mrcoffee.domain.models.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.omarhawari.mrcoffee.domain.models.Extra
import com.omarhawari.mrcoffee.domain.models.Size
import com.omarhawari.mrcoffee.domain.models.Type


data class TypeWithExtras(
    @Embedded val type: Type,
    @Relation(
        parentColumn = "typeId",
        entityColumn = "extraId",
        associateBy = Junction(TypeExtraCrossRef::class)
    ) val extras: List<Extra>
)