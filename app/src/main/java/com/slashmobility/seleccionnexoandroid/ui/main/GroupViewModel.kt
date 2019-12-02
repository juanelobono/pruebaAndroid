package com.slashmobility.seleccionnexoandroid.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.slashmobility.seleccionnexoandroid.database.daos.GroupDao
import com.slashmobility.seleccionnexoandroid.domain.GroupRepository
import com.slashmobility.seleccionnexoandroid.models.Group
import com.slashmobility.seleccionnexoandroid.remote.ApiResponse
import com.slashmobility.seleccionnexoandroid.remote.api.ApiClient
import com.slashmobility.seleccionnexoandroid.remote.responses.GroupResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-10-09
 */


class GroupViewModel @Inject constructor(
    groupDao: GroupDao,
    apiClient: ApiClient
): ViewModel() {

    private val groupRepository: GroupRepository =
        GroupRepository(groupDao, apiClient)

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
        val json = Gson().fromJson(jsonElement, Group::class.java)
        return arrayListOf()
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

    /**
     * Clear disposables
     */

    override fun onCleared() {
        disposables.clear()
    }
}