package com.slashmobility.seleccionnexoandroid.database.repositories

import com.slashmobility.seleccionnexoandroid.models.GroupImages


/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */

interface GroupImagesRepositoryImp {

    fun getGroupImages(): List<GroupImages>

    fun getGroupImagesById(groupImageId: Long?): GroupImages?

    fun deleteGroupImages(groupImage: GroupImages)

    fun deleteAllGroupsImages()

    fun addGroupImages(groupImage: GroupImages)

    fun updateGroupImages(groupImage: GroupImages)

}