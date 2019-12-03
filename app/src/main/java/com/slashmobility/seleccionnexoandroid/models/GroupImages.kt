package com.slashmobility.seleccionnexoandroid.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize

/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */

@Entity(tableName = "GroupImages")
@Parcelize
data class GroupImages(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var images: ArrayList<String>? = null
    ) : Parcelable

class ImagesConverter {

    private val gson = Gson()

    @TypeConverter
    fun arrayListToJson(list: ArrayList<String>?): String? {
        return if(list == null) null else gson.toJson(list)
    }

    @TypeConverter
    fun jsonToArrayList(jsonData: String?): ArrayList<String>? {
        return if (jsonData == null) null else gson.fromJson(jsonData, object : TypeToken<ArrayList<String>?>() {}.type)
    }
}