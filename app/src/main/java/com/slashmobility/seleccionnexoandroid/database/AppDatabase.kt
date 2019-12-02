package com.slashmobility.seleccionnexoandroid.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.slashmobility.seleccionnexoandroid.database.daos.GroupDao
import com.slashmobility.seleccionnexoandroid.database.daos.GroupImagesDao
import com.slashmobility.seleccionnexoandroid.models.Group
import com.slashmobility.seleccionnexoandroid.models.GroupImages
import com.slashmobility.seleccionnexoandroid.models.ImagesConverter

/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */

@Database(entities = [Group::class, GroupImages::class], version = 3, exportSchema = false)
@TypeConverters(ImagesConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao

    abstract fun groupImagesDao(): GroupImagesDao

    companion object {
        const val DB_NAME = "Test.db"
    }
}
