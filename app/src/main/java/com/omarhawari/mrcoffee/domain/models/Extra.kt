package com.omarhawari.mrcoffee.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Extra(
    @PrimaryKey val extraId: String,
    val name: String
)