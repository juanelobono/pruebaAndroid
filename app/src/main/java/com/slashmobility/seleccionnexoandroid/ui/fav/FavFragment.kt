package com.slashmobility.seleccionnexoandroid.ui.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slashmobility.seleccionnexoandroid.R
import com.slashmobility.seleccionnexoandroid.models.Group
import com.slashmobility.seleccionnexoandroid.ui.main.GroupFragment
import com.slashmobility.seleccionnexoandroid.ui.main.GroupsAdapter

/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */

class FavFragment: Fragment() {

    companion object {

        private val TAG = FavFragment::class.java.simpleName + " ========>"
    }

    private var favGroups: ArrayList<Group>? = null
    private lateinit var rvFavGroups: RecyclerView
    private lateinit var favGroupsAdapter: GroupsAdapter
    private lateinit var llEmptyFavGroups: LinearLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        favGroups = arguments?.getParcelableArrayList<Group>(GroupFragment.PARAM_FAV_GROUPS)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fav, container, false)

        initView(view)

        setupRecycler()

        return view
    }

    override fun onPrepareOptionsMenu( menu : Menu) {
        menu.findItem(R.id.manuFav).isVisible = false
        menu.findItem(R.id.manuRefresh).isVisible = false
        super.onPrepareOptionsMenu(menu);
    }

    private fun initView(view: View) {
        toolbar = activity!!.findViewById(R.id.mainToolbar) as Toolbar
        toolbar.title = getString(R.string.title_fav_fragment)
        toolbar.navigationIcon = ContextCompat.getDrawable(context!!, R.drawable.abc_ic_ab_back_material)

        toolbar.setNavigationOnClickListener{ activity?.onBackPressed() }

        rvFavGroups = view.findViewById(R.id.rvFavGroups) as RecyclerView
        llEmptyFavGroups = view.findViewById(R.id.llEmptyFavGroups) as LinearLayout
    }

    private fun setupRecycler() {

        favGroups?.let { groupsData ->

            if (groupsData.isNotEmpty()) {
                favGroupsAdapter = GroupsAdapter(groupsData, context!!)
                rvFavGroups.layoutManager =
                    LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
                rvFavGroups.adapter = favGroupsAdapter

            } else {

                rvFavGroups.visibility = View.GONE
                llEmptyFavGroups.visibility = View.VISIBLE
            }
        }
    }
}