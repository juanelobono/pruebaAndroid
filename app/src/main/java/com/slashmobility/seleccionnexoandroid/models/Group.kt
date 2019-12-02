package com.slashmobility.seleccionnexoandroid.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */

@Entity(tableName = "Groups")
@Parcelize
data class Group(
    @SerializedName("id") @PrimaryKey var id: Long = 0,
    @SerializedName("date") var date: Long = 0,
    @SerializedName("defaultImageUrl") var imageUrl: String = "",
    @SerializedName("description") var description: String = "",
    @SerializedName("descriptionShort") var shortDescription: String = "",
    @SerializedName("name") var name: String = "",
    var isFavorite: Boolean = false
    ) : Parcelable {

    fun customMethod(): String {

        return ""
    }
}