package com.slashmobility.seleccionnexoandroid.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */

@Entity(tableName = "GroupImages")
@Parcelize
data class GroupImages(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var images: ArrayList<String>? = null
    ) : Parcelable {

    fun customMethod(): String {

        return ""
    }
}