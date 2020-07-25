package com.hypheno.imageloader.model.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo")
data class Photo(
    val farm: Int,
    val id: String,
    val isfamily: Int,
    val isfriend: Int,
    val ispublic: Int,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String
) {
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}