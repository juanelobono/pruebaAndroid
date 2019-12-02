package com.slashmobility.seleccionnexoandroid.ui.fav

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.slashmobility.seleccionnexoandroid.R
import com.slashmobility.seleccionnexoandroid.factory.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */

class FavFragment: Fragment() {

    companion object {

        private val TAG = FavFragment::class.java.simpleName + " ========>"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: FavViewModel
    private lateinit var rvFavGroups: RecyclerView
    private lateinit var llEmptyFavGroups: LinearLayout

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
        val view = inflater.inflate(R.layout.fragment_fav, container, false)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FavViewModel::class.java)

        initView(view)

        val favs = viewModel.getFavsFromDB()

        return view
    }

    private fun initView(view: View) {

        rvFavGroups = view.findViewById<View>(R.id.rvFavGroups) as RecyclerView
        llEmptyFavGroups = view.findViewById<View>(R.id.llEmptyFavGroups) as LinearLayout
    }
}