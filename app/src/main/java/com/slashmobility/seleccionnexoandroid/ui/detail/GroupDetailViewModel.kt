package com.slashmobility.seleccionnexoandroid.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonElement
import com.slashmobility.seleccionnexoandroid.BuildConfig
import com.slashmobility.seleccionnexoandroid.database.daos.GroupDao
import com.slashmobility.seleccionnexoandroid.database.daos.GroupImagesDao
import com.slashmobility.seleccionnexoandroid.domain.GroupRepository
import com.slashmobility.seleccionnexoandroid.models.Group
import com.slashmobility.seleccionnexoandroid.models.GroupImages
import com.slashmobility.seleccionnexoandroid.remote.ApiResponse
import com.slashmobility.seleccionnexoandroid.remote.api.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */


class GroupDetailViewModel @Inject constructor(
    groupDao: GroupDao,
    groupImagesDao: GroupImagesDao,
    apiClient: ApiClient
): ViewModel() {

    private val groupRepository: GroupRepository =
        GroupRepository(groupDao, groupImagesDao, apiClient)

    private val disposables = CompositeDisposable()
    private val groupImagesResponseLiveData = MutableLiveData<ApiResponse>()

    /**
     * Requests
     */

    fun getGroupImagesRequest(groupId: Long) {

        val url = getImagesUrl(groupId)
        disposables.add(groupRepository.getGroupImages(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { groupImagesResponseLiveData.setValue(ApiResponse.loading()) }
            .subscribe(
                { result -> groupImagesResponseLiveData.setValue(ApiResponse.success(result)) },
                { throwable -> groupImagesResponseLiveData.setValue(ApiResponse.error(throwable)) }
            ))
    }

    private fun getImagesUrl(groupId: Long): String {
        return BuildConfig.API_HOST + "images/$groupId.json"
    }

    /**
     * LiveData responses
     */

    fun getGroupImagesResponse(): MutableLiveData<ApiResponse> {
        return groupImagesResponseLiveData
    }

    /**
     * JSON responses to Object
     */

    fun getGroupImages(jsonElement: JsonElement): GroupImages {

        val groupImages = GroupImages()
        groupImages.images = arrayListOf()

        val array = jsonElement.asJsonArray

        array.forEach { item ->

            Log.d("Item", item.toString())
            groupImages.images?.add(item.toString())
        }

        return groupImages
    }

    /**
     * Local repository
     */

    fun getGroupImagesByIdFromDB(groupId: Long): GroupImages? {
        return groupRepository.getGroupImagesById(groupId)
    }

    fun getGroupImagesFromDB(): List<GroupImages> {
        return groupRepository.getGroupImages()
    }

    fun addGroupImagesToDB(groupImages: GroupImages) {
        groupRepository.addGroupImages(groupImages)
    }

    fun setFav(group: Group) {
        group.isFavorite = true
        groupRepository.updateGroup(group)
    }

    fun deleteFav(group: Group) {
        group.isFavorite = false
        groupRepository.updateGroup(group)
    }

    /**
     * Clear disposables
     */

    override fun onCleared() {
        disposables.clear()
    }
}