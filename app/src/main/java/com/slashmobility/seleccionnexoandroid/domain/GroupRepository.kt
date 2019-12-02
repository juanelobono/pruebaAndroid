package com.slashmobility.seleccionnexoandroid.domain

import com.google.gson.JsonElement
import com.slashmobility.seleccionnexoandroid.database.daos.GroupDao
import com.slashmobility.seleccionnexoandroid.database.daos.GroupImagesDao
import com.slashmobility.seleccionnexoandroid.database.repositories.GroupImagesRepositoryImp
import com.slashmobility.seleccionnexoandroid.database.repositories.GroupRepositoryImp
import com.slashmobility.seleccionnexoandroid.models.Group
import com.slashmobility.seleccionnexoandroid.models.GroupImages
import com.slashmobility.seleccionnexoandroid.remote.api.ApiClient
import io.reactivex.Observable
import javax.inject.Singleton

/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */

@Singleton
class GroupRepository(
    private val groupDao: GroupDao,
    private val groupImagesDao: GroupImagesDao,
    private val apiClient: ApiClient
): GroupRepositoryImp,
    GroupImagesRepositoryImp {

    /**
     * Local database
     */

    //Groups

    override fun getGroups(): List<Group> {
        return groupDao.getAll()
    }

    override fun getFavGroups(): List<Group> {
        return groupDao.getFavs()
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

    //Group images

    override fun getGroupImages(): List<GroupImages> {
        return groupImagesDao.getAll()
    }

    override fun getGroupImagesById(groupImageId: Long?): GroupImages? {

        groupImageId?.let {

            return groupImagesDao.getById(groupImageId)
        }

        return null
    }

    override fun deleteGroupImages(groupImage: GroupImages) {
        groupImagesDao.delete(groupImage)
    }

    override fun deleteAllGroupsImages() {
        groupImagesDao.deleteAll()
    }

    override fun addGroupImages(groupImage: GroupImages) {
        groupImagesDao.insert(groupImage)
    }

    override fun updateGroupImages(groupImage: GroupImages) {
        groupImagesDao.update(groupImage)
    }

    /**
     * Remote api
     */

    fun getGroupList(): Observable<JsonElement> {
        return apiClient.getGroupList()
    }

    fun getGroupImages(url: String): Observable<JsonElement> {
        return apiClient.getGroupImages(url)
    }
}