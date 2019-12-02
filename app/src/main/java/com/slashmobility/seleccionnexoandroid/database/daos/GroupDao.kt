package com.slashmobility.seleccionnexoandroid.database.daos

import androidx.room.*
import com.slashmobility.seleccionnexoandroid.models.Group

@Dao
interface GroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(group: Group)

    @Update
    fun update(group: Group)

    @Delete
    fun delete(group: Group)

    @Query("DELETE FROM Groups")
    fun deleteAll()

    @Query("SELECT * FROM Groups ORDER BY id DESC")
    fun getAll(): List<Group>

    @Query("SELECT * FROM Groups WHERE id = :id LIMIT 1")
    fun getById(id: Long): Group

    @Query("SELECT * FROM Groups LIMIT :limit OFFSET :offset")
    fun getByLimit(limit: Int, offset: Int): List<Group>
}