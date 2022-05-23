package com.omarhawari.mrcoffee.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omarhawari.mrcoffee.domain.models.Extra
import com.omarhawari.mrcoffee.domain.models.Size
import com.omarhawari.mrcoffee.domain.models.SubSelection
import com.omarhawari.mrcoffee.domain.models.Type
import com.omarhawari.mrcoffee.domain.models.relations.TypeExtraCrossRef
import com.omarhawari.mrcoffee.domain.models.relations.TypeSizeCrossRef


@Database(
    entities = [
        Extra::class,
        Size::class,
        SubSelection::class,
        Type::class,
        TypeSizeCrossRef::class,
        TypeExtraCrossRef::class,
    ],
    version = 1,
)
abstract class CoffeeDatabase : RoomDatabase() {

    abstract val dao: CoffeeDao

    companion object {
        const val DATABASE_NAME = "coffee_db"
    }

}