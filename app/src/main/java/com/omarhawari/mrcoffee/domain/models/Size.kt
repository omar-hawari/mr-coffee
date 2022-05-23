package com.omarhawari.mrcoffee.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Size(
    @PrimaryKey(autoGenerate = false) val sizeId: String,
    val name: String
) {
    override fun toString(): String {
        return "Size(sizeId='$sizeId', name='$name')"
    }
}