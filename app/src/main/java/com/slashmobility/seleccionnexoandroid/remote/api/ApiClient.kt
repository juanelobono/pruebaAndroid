package com.slashmobility.seleccionnexoandroid.remote.api

import com.google.gson.JsonElement
import com.slashmobility.seleccionnexoandroid.utils.Urls
import io.reactivex.Observable
import retrofit2.http.*


/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-10-09
 */

interface ApiClient {

    @GET(Urls.GROUPS)
    fun getGroupList(): Observable<JsonElement>

    @GET(Urls.IMAGES)
    fun getGroupImages(
        @Query("groupId") groupId: Long
    ): Observable<JsonElement>

}