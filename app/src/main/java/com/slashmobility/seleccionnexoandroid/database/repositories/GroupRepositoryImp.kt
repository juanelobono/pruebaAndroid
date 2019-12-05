package com.slashmobility.seleccionnexoandroid.database.repositories

import com.slashmobility.seleccionnexoandroid.models.Group


/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */

interface GroupRepositoryImp {

    fun getGroups(): List<Group>

    fun getFavGroups(): List<Group>

    fun getGroupById(groupId: Long?): Group?

    fun deleteGroup(group: Group)

    fun deleteAllGroups()

    fun addGroup(group: Group)

    fun updateGroup(group: Group)

}