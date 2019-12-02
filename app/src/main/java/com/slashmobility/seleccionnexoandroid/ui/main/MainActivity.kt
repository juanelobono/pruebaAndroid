package com.slashmobility.seleccionnexoandroid.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.slashmobility.seleccionnexoandroid.R
import com.slashmobility.seleccionnexoandroid.models.Group
import com.slashmobility.seleccionnexoandroid.ui.detail.GroupDetailFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IShowGroupDetail {

    override fun show(group: Group) {
        val fragment = GroupDetailFragment()
            .apply {
            arguments = Bundle().apply {
                putParcelable(PARAM_GROUP, group)
            }
        }
        loadFragment(fragment)
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName + " ========>"
        const val PARAM_GROUP = "group"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        loadFragment(GroupFragment())
    }

    private fun loadFragment(fragment: Fragment?) {
        //Switching fragment
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        return when (item.itemId) {

            R.id.manuRefresh ->  {
                false
            }

            else -> false
        }
    }
}
