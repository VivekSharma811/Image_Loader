package com.hypheno.imageloader.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hypheno.imageloader.model.dataclass.Photo

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg photo : Photo)

    @Query("SELECT * FROM photo")
    fun getAllPhotos() : LiveData<List<Photo>>

    @Query("DELETE FROM photo")
    fun deleteAll()

}