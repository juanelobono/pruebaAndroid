package com.slashmobility.seleccionnexoandroid.ui.fav

import androidx.lifecycle.ViewModel
import com.slashmobility.seleccionnexoandroid.database.daos.GroupDao
import com.slashmobility.seleccionnexoandroid.database.daos.GroupImagesDao
import com.slashmobility.seleccionnexoandroid.domain.GroupRepository
import com.slashmobility.seleccionnexoandroid.models.Group
import com.slashmobility.seleccionnexoandroid.remote.api.ApiClient
import javax.inject.Inject


/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */


class FavViewModel @Inject constructor(
    groupDao: GroupDao,
    groupImagesDao: GroupImagesDao,
    apiClient: ApiClient
): ViewModel() {

    private val groupRepository: GroupRepository =
        GroupRepository(groupDao, groupImagesDao, apiClient)

    /**
     * Local repository
     */

    fun getFavsFromDB(): List<Group> {
        return groupRepository.getFavGroups()
    }
}