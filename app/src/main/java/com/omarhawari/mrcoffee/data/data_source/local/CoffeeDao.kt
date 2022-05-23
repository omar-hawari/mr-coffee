package com.omarhawari.mrcoffee.data.data_source.local

import androidx.room.*
import com.omarhawari.mrcoffee.domain.models.Extra
import com.omarhawari.mrcoffee.domain.models.Size
import com.omarhawari.mrcoffee.domain.models.SubSelection
import com.omarhawari.mrcoffee.domain.models.Type
import com.omarhawari.mrcoffee.domain.models.relations.*

@Dao
interface CoffeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertType(type: Type)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExtra(extra: Extra)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSize(size: Size)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubSelection(subSelection: SubSelection)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTypeSizeCrossRef(crossRef: TypeSizeCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTypeExtraCrossRef(crossRef: TypeExtraCrossRef)


    @Transaction
    @Query("SELECT * FROM type")
    suspend fun getTypes(): List<Type>

    @Transaction
    @Query("SELECT * FROM type WHERE typeId = :typeId")
    suspend fun getSizesOfType(typeId: String): List<TypeWithSizes>

    @Transaction
    @Query("SELECT * FROM type WHERE typeId = :typeId")
    suspend fun getExtrasOfType(typeId: String): List<TypeWithExtras>

    @Transaction
    @Query("SELECT * FROM extra WHERE extraId = :extraId")
    suspend fun getSubSelectionsOfExtra(extraId: String): List<ExtraWithSubSelections>


}