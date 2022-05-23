package com.omarhawari.mrcoffee.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SubSelection(
    @PrimaryKey val subSelectionId: String,
    val name: String,
    var extraId: String = "",
)