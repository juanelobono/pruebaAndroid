package com.slashmobility.seleccionnexoandroid.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slashmobility.seleccionnexoandroid.R
import com.slashmobility.seleccionnexoandroid.factory.ViewModelFactory
import com.slashmobility.seleccionnexoandroid.models.Group
import com.slashmobility.seleccionnexoandroid.remote.ApiResponse
import com.slashmobility.seleccionnexoandroid.ui.detail.GroupDetailFragment
import com.slashmobility.seleccionnexoandroid.ui.fav.FavFragment
import com.slashmobility.seleccionnexoandroid.utils.Status
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */

class GroupFragment: Fragment() {

    companion object {

        private val TAG = GroupFragment::class.java.simpleName + " ========>"
        const val PARAM_GROUP = "group"
        const val PARAM_FAV_GROUPS = "favGroups"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: GroupViewModel
    private lateinit var rlProgress: RelativeLayout
    private lateinit var rvGroups: RecyclerView
    private lateinit var llEmptyGroups: LinearLayout
    private lateinit var groupsAdapter: GroupsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_groups, container, false)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GroupViewModel::class.java)

        initView(view)
        setupToolbar()
        getGroupList()

        return view
    }

    private fun getGroupList() {

        viewModel.getGroupListRequest()

        viewModel.getGroupListResponse()
            .observe(viewLifecycleOwner, Observer<ApiResponse> { apiResponse ->

                when (apiResponse.status) {

                    Status.LOADING -> rlProgress.visibility = View.VISIBLE

                    Status.SUCCESS -> {

                        rlProgress.visibility = View.GONE

                        val groups = viewModel.getGroupList(apiResponse.data!!)

                        groups.forEach { groupData ->

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

                        setupView(groups)
                    }

                    Status.ERROR -> {
                        Log.d(TAG, apiResponse.error.toString())
                        rlProgress.visibility = View.GONE

                    }
                }
            })
    }

    private fun initView(view: View) {
        rlProgress = view.findViewById<View>(R.id.rlProgress) as RelativeLayout
        rvGroups = view.findViewById<View>(R.id.rvGroups) as RecyclerView
        llEmptyGroups = view.findViewById<View>(R.id.llEmptyGroups) as LinearLayout
    }

    private fun setupView(groups: List<Group>?) {

        groups?.let { groupsData ->

            if (groupsData.isNotEmpty()) {
                groupsAdapter = GroupsAdapter(groupsData, context!!)
                rvGroups.layoutManager =
                    LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
                rvGroups.adapter = groupsAdapter

                groupsAdapter.onItemClick = { group ->

                    goToDetail(group)
                }

            } else {

                rvGroups.visibility = View.GONE
                llEmptyGroups.visibility = View.VISIBLE
            }
        }
    }

    private fun goToDetail(group: Group) {

        val fragment = GroupDetailFragment()
            .apply {
                arguments = Bundle().apply {
                    putParcelable(PARAM_GROUP, group)
                }
            }

        addFragment(fragment)
    }

    private fun goToFav() {

        val favGroups = viewModel.getFavsFromDB()
        val fragment = FavFragment()
            .apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(PARAM_FAV_GROUPS, favGroups)
                }
            }

        addFragment(fragment)
    }

    private fun addFragment(fragment: Fragment) {

        val ft = parentFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(tag)
        ft.setReorderingAllowed(true)
        ft.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        return when (item.itemId) {

            R.id.manuRefresh ->  {
                getGroupList()
                true
            }
            R.id.manuFav -> {
                //Go Fragment Fav
                val fragment = FavFragment()
                val transaction = activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.container, fragment)
                transaction.commit()
                true
            }


            R.id.manuFav ->  {
                goToFav()
                true
            }

            else -> false
        }
    }

    private fun setupToolbar(){
        //Setup Toolbar MainActivity
        val toolbar = activity!!.findViewById<View>(R.id.mainToolbar) as Toolbar
        toolbar.title = getString(R.string.title_main_activity)
        toolbar.setTitleTextColor(ContextCompat.getColor(context!!, R.color.md_white_1000))

        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayShowHomeEnabled(false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)

    }
}