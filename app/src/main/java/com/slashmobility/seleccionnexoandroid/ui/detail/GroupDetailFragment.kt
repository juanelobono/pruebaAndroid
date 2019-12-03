package com.slashmobility.seleccionnexoandroid.ui.detail

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.slashmobility.seleccionnexoandroid.R
import com.slashmobility.seleccionnexoandroid.extensions.isConnectedToNetwork
import com.slashmobility.seleccionnexoandroid.extensions.loadImage
import com.slashmobility.seleccionnexoandroid.factory.ViewModelFactory
import com.slashmobility.seleccionnexoandroid.models.Group
import com.slashmobility.seleccionnexoandroid.models.GroupImages
import com.slashmobility.seleccionnexoandroid.remote.ApiResponse
import com.slashmobility.seleccionnexoandroid.ui.main.GroupFragment
import com.slashmobility.seleccionnexoandroid.ui.main.IShowAppBar
import com.slashmobility.seleccionnexoandroid.ui.main.MainActivity
import com.slashmobility.seleccionnexoandroid.ui.main.MainActivity.Companion.IMAGES_GROUP
import com.slashmobility.seleccionnexoandroid.utils.DateUtils
import com.slashmobility.seleccionnexoandroid.utils.Status
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */

class GroupDetailFragment: Fragment() {

    companion object {

        private val TAG = GroupDetailFragment::class.java.simpleName + " ========>"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: GroupDetailViewModel
    private lateinit var rlProgress: RelativeLayout
    private lateinit var ivImage: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvDescription: TextView
    private lateinit var ivFav: AppCompatImageView
    private lateinit var tvDescriptionLong: TextView
    private lateinit var toolbar: Toolbar

    private var group: Group? = null

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

        group = arguments?.getParcelable(GroupFragment.PARAM_GROUP)
    }

    override fun onPrepareOptionsMenu( menu : Menu) {
        menu.findItem(R.id.manuFav).isVisible = false
        menu.findItem(R.id.manuRefresh).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        eventListener.show(true)
        exitFullscreen(activity!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GroupDetailViewModel::class.java)

        initView(view)

        group?.id?.let { id ->

            val isConnected = activity?.isConnectedToNetwork() ?: false

            if (isConnected) {

                getGroupList(id)

            } else {

                rlProgress.visibility = View.GONE
                val images = viewModel.getGroupImagesByIdFromDB(id)

                images?.let {

                    setupView(it)

                } ?: run {

                    Log.e(TAG, "Group images null")
                }

            }
        }

        observeResult()

        ivFav.setOnClickListener {

            group?.isFavorite?.let { isFavorite ->

                val fav = !isFavorite

                if (fav) {

                    ivFav.setImageResource(R.drawable.ic_favorite_black)
                    viewModel.setFav(group!!)

                } else {

                    ivFav.setImageResource(R.drawable.ic_favorite_border_black)
                    viewModel.deleteFav(group!!)
                }
            }
        }

        return view
    }

    private fun observeResult() {

        viewModel.getGroupImagesResponse()
            .observe(viewLifecycleOwner, Observer<ApiResponse> { apiResponse ->

                when (apiResponse.status) {

                    Status.LOADING -> rlProgress.visibility = View.VISIBLE

                    Status.SUCCESS -> {

                        rlProgress.visibility = View.GONE

                        val images = viewModel.getGroupImages(apiResponse.data!!)
                        images.id = group?.id!!

                        viewModel.addGroupImagesToDB(images)

                        setupView(images)
                    }

                    Status.ERROR -> {
                        Log.d(TAG, apiResponse.error.toString())
                        rlProgress.visibility = View.GONE

                    }
                }
            })
    }

    private fun initView(view: View) {
        toolbar = activity!!.findViewById(R.id.mainToolbar) as Toolbar
        toolbar.navigationIcon = ContextCompat.getDrawable(context!!, R.drawable.abc_ic_ab_back_material)

        toolbar.setNavigationOnClickListener{ activity?.onBackPressed() }

        rlProgress = view.findViewById<View>(R.id.rlProgress) as RelativeLayout
        ivImage = view.findViewById<View>(R.id.ivImage) as ImageView
        tvName = view.findViewById<View>(R.id.tvName) as TextView
        tvDate = view.findViewById<View>(R.id.tvDate) as TextView
        tvDescription = view.findViewById<View>(R.id.tvDescription) as TextView
        ivFav = view.findViewById<View>(R.id.ivFav) as AppCompatImageView
        tvDescriptionLong = view.findViewById<View>(R.id.tvDescriptionLong) as TextView
    }

    private fun getGroupList(groupId: Long) {

        viewModel.getGroupImagesRequest(groupId)
    }

    private fun setupView(groupImages: GroupImages) {
        toolbar.title = group?.name

        tvName.text = group?.name
        tvDate.text = group?.date?.let { DateUtils.getDateTime(it) }
        tvDescription.text = group?.shortDescription

        group?.isFavorite?.let { isFavorite ->

            if (isFavorite) {

                ivFav.setImageResource(R.drawable.ic_favorite_black)

            } else {

                ivFav.setImageResource(R.drawable.ic_favorite_border_black)
            }
        }

        //Load image and save in cache
        group?.imageUrl?.let { url ->

            ivImage.loadImage(url)
        }

        ivImage.setOnClickListener {
            //Go Fragment Fav
            val fragment = GroupImagesFragment()
                .apply {
                    arguments = Bundle().apply {
                        putParcelable(IMAGES_GROUP, groupImages)
                    }
                }

            addFragment(fragment)
        }
    }

    private fun addFragment(fragment: Fragment) {

        val ft = parentFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(tag)
        ft.setReorderingAllowed(true)
        ft.commit()
    }

    private fun exitFullscreen(activity: Activity) {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}