package com.slashmobility.seleccionnexoandroid.ui.detail

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.slashmobility.seleccionnexoandroid.R
import com.slashmobility.seleccionnexoandroid.models.GroupImages
import com.slashmobility.seleccionnexoandroid.ui.main.IShowAppBar
import com.slashmobility.seleccionnexoandroid.ui.main.MainActivity
import dagger.android.support.AndroidSupportInjection

class GroupImagesFragment : Fragment(){

    companion object {

        private val TAG = GroupImagesFragment::class.java.simpleName + " ========>"
    }

    private lateinit var viewPage: ViewPager
    private lateinit var imagesAdapter : ImageAdapter
    private lateinit var groupImages : GroupImages
    private lateinit var eventListener: IShowAppBar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

        try {
            eventListener = activity as MainActivity
        } catch (e: ClassCastException) {
            throw ClassCastException()
        }
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
        //Setup Adapter and ViewPage
        imagesAdapter = ImageAdapter(context!!, groupImages.images)
        viewPage.adapter = imagesAdapter
    }

    private fun setupToolbar() {
        //Setup Toolbar MainActivity
        eventListener.show(false)
        setFullscreen(activity!!)
    }

    private fun isImmersiveAvailable(): Boolean {
        return Build.VERSION.SDK_INT >= 19
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun setFullscreen(activity : Activity) {
        var flags = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        if (isImmersiveAvailable()) {
            flags = View.SYSTEM_UI_FLAG_IMMERSIVE or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        activity.window.decorView.systemUiVisibility = flags
    }

}