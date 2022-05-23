package com.omarhawari.mrcoffee.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Type(
    @PrimaryKey(autoGenerate = false)
    val typeId: String,
    val name: String
)