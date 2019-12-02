package com.slashmobility.seleccionnexoandroid.domain

import com.google.gson.JsonElement
import com.slashmobility.seleccionnexoandroid.database.daos.GroupDao
import com.slashmobility.seleccionnexoandroid.database.repositories.GroupRepositoryImp
import com.slashmobility.seleccionnexoandroid.models.Group
import com.slashmobility.seleccionnexoandroid.remote.api.ApiClient
import io.reactivex.Observable
import javax.inject.Singleton

/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */

@Singleton
class GroupRepository(
    private val groupDao: GroupDao,
    private val apiClient: ApiClient
):
    GroupRepositoryImp {

    /**
     * Local database
     */

    override fun getGroups(): List<Group> {
        return groupDao.getAll()
    }

    override fun getGroupById(groupId: Long?): Group? {

        groupId?.let {

            return groupDao.getById(groupId)
        }

        return null
    }

    override fun deleteGroup(group: Group) {
        groupDao.delete(group)
    }

    override fun deleteAllGroups() {
        groupDao.deleteAll()
    }

    override fun addGroup(group: Group) {
        groupDao.insert(group)
    }

    override fun updateGroup(group: Group) {
        groupDao.update(group)
    }

    /**
     * Remote api
     */

    fun getGroupList(): Observable<JsonElement> {
        return apiClient.getGroupList()
    }

    fun getGroupImages(groupId: Long): Observable<JsonElement> {
        return apiClient.getGroupImages(groupId)
    }
}