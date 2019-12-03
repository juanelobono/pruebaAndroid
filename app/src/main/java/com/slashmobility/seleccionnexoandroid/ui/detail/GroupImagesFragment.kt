package com.slashmobility.seleccionnexoandroid.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.slashmobility.seleccionnexoandroid.R
import com.slashmobility.seleccionnexoandroid.models.GroupImages
import com.slashmobility.seleccionnexoandroid.ui.main.MainActivity
import dagger.android.support.AndroidSupportInjection

class GroupImagesFragment : Fragment() {

    companion object {

        private val TAG = GroupImagesFragment::class.java.simpleName + " ========>"
    }

    private lateinit var viewPage: ViewPager
    private lateinit var imagesAdapter : ImageAdapter
    private lateinit var groupImages : GroupImages

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        groupImages = arguments?.getParcelable(MainActivity.IMAGES_GROUP)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_images_group, container, false)

        initView(view)
        setupToolbar()

        return view
    }

    private fun initView(view: View) {
        viewPage = view.findViewById(R.id.viewPager)
        imagesAdapter = ImageAdapter(context!!, groupImages.images)
        viewPage.adapter = imagesAdapter
    }

    private fun setupToolbar(){
        //Setup Toolbar MainActivity
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }
}