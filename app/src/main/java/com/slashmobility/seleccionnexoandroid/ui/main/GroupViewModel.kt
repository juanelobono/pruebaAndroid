package com.slashmobility.seleccionnexoandroid.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.slashmobility.seleccionnexoandroid.database.daos.GroupDao
import com.slashmobility.seleccionnexoandroid.database.daos.GroupImagesDao
import com.slashmobility.seleccionnexoandroid.domain.GroupRepository
import com.slashmobility.seleccionnexoandroid.models.Group
import com.slashmobility.seleccionnexoandroid.remote.ApiResponse
import com.slashmobility.seleccionnexoandroid.remote.api.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */


class GroupViewModel @Inject constructor(
    groupDao: GroupDao,
    groupImagesDao: GroupImagesDao,
    apiClient: ApiClient
): ViewModel() {

    private val groupRepository: GroupRepository =
        GroupRepository(groupDao, groupImagesDao, apiClient)

    private val disposables = CompositeDisposable()
    private val groupListResponseLiveData = MutableLiveData<ApiResponse>()

    /**
     * Requests
     */

    fun getGroupListRequest() {

        disposables.add(groupRepository.getGroupList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { groupListResponseLiveData.setValue(ApiResponse.loading()) }
            .subscribe(
                { result -> groupListResponseLiveData.setValue(ApiResponse.success(result)) },
                { throwable -> groupListResponseLiveData.setValue(ApiResponse.error(throwable)) }
            ))
    }

    /**
     * LiveData responses
     */

    fun getGroupListResponse(): MutableLiveData<ApiResponse> {
        return groupListResponseLiveData
    }

    /**
     * JSON responses to Object
     */

    fun getGroupList(jsonElement: JsonElement): List<Group> {

        val groupList: ArrayList<Group> = arrayListOf()

        val array = jsonElement.asJsonArray

        array.forEach { item ->

            Log.d("Item", item.toString())
            val group = Gson().fromJson(item, Group::class.java)
            groupList.add(group)
        }

        return groupList
    }

    /**
     * Local repository
     */

    fun getGroupListFromDB(): List<Group> {
        return groupRepository.getGroups()
    }

    fun addGroupToDB(group: Group) {
        groupRepository.addGroup(group)
    }

    fun getFavsFromDB(): ArrayList<Group> {
        val array = arrayListOf<Group>()
        array.addAll(groupRepository.getFavGroups())
        return array
    }

    fun getGroupById(groupId: Long?): Group?{
        return groupRepository.getGroupById(groupId)
    }


    /**
     * Clear disposables
     */

    override fun onCleared() {
        disposables.clear()
    }
}