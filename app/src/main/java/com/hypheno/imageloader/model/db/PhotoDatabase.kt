package com.hypheno.imageloader.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hypheno.imageloader.model.dataclass.Photo

@Database(
    entities = arrayOf(Photo::class),
    version = 1
)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun photoDao() : PhotoDao

    companion object {
        @Volatile private var instance : PhotoDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context : Context) =
            Room.databaseBuilder(context.applicationContext,
                PhotoDatabase::class.java,
            "photo.db")
                .build()
    }

}