package com.slashmobility.seleccionnexoandroid.database.daos

import androidx.room.*
import com.slashmobility.seleccionnexoandroid.models.GroupImages

@Dao
interface GroupImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(group: GroupImages)

    @Update
    fun update(group: GroupImages)

    @Delete
    fun delete(group: GroupImages)

    @Query("DELETE FROM GroupImages")
    fun deleteAll()

    @Query("SELECT * FROM GroupImages ORDER BY id DESC")
    fun getAll(): List<GroupImages>

    @Query("SELECT * FROM GroupImages WHERE id = :id LIMIT 1")
    fun getById(id: Long): GroupImages

    @Query("SELECT * FROM GroupImages LIMIT :limit OFFSET :offset")
    fun getByLimit(limit: Int, offset: Int): List<GroupImages>
}