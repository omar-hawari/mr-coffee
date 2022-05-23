package com.omarhawari.mrcoffee.domain.models.relations

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.Relation
import com.omarhawari.mrcoffee.domain.models.Extra
import com.omarhawari.mrcoffee.domain.models.SubSelection


/**
 * One (Extra) to Many (SubSelection) Relation
 *
* */

data class ExtraWithSubSelections(
    @Embedded val extra: Extra,
    @Relation(
        parentColumn = "extraId",
        entityColumn = "extraId",
    ) val subSelections: List<SubSelection>,
)

fun ExtraWithSubSelections.toExtraWithSubSelectionsSelected(): ExtraWithSubSelectionsSelected {
    return ExtraWithSubSelectionsSelected(
        extra, subSelections
    )
}

data class ExtraWithSubSelectionsSelected(
    val extra: Extra,
    val subSelections: List<SubSelection>,
    var selectedIndex: Int = -1
)