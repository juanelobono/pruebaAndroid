package com.slashmobility.seleccionnexoandroid.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.slashmobility.seleccionnexoandroid.R
import com.slashmobility.seleccionnexoandroid.factory.ViewModelFactory
import com.slashmobility.seleccionnexoandroid.models.Group
import com.slashmobility.seleccionnexoandroid.remote.ApiResponse
import com.slashmobility.seleccionnexoandroid.utils.Status
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */

class GroupFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: GroupViewModel
    private lateinit var rlProgress: RelativeLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_groups, container, false)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GroupViewModel::class.java)

        rlProgress = view.findViewById<View>(R.id.rlProgress) as RelativeLayout

        getGroupList()

        return view
    }

    private fun getGroupList() {

        viewModel.getGroupListRequest()

        viewModel.getGroupListResponse().observe(viewLifecycleOwner, Observer<ApiResponse> { apiResponse ->

            when (apiResponse.status) {

                Status.LOADING -> rlProgress.visibility = View.VISIBLE

                Status.SUCCESS -> {

                    rlProgress.visibility = View.GONE

                    val groups = viewModel.getGroupList(apiResponse.data!!)

                    groups?.forEach { groupData ->

                        val group = Group()
                        group.apply {

                            id = groupData.id
                            date = groupData.date
                            imageUrl = groupData.imageUrl
                            description = groupData.description
                            shortDescription = groupData.shortDescription
                            name = groupData.name
                        }

                        viewModel.addGroupToDB(group)
                    }

                }

                Status.ERROR -> {
                    apiResponse.error
                    rlProgress.visibility = View.GONE

                }
            }
        })
    }

}