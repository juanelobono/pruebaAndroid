package com.slashmobility.seleccionnexoandroid.ui.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.slashmobility.seleccionnexoandroid.R
import com.slashmobility.seleccionnexoandroid.factory.ViewModelFactory
import com.slashmobility.seleccionnexoandroid.models.Group
import com.slashmobility.seleccionnexoandroid.models.GroupImages
import com.slashmobility.seleccionnexoandroid.remote.ApiResponse
import com.slashmobility.seleccionnexoandroid.ui.main.MainActivity
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

    private var group: Group? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        group = arguments?.getParcelable(MainActivity.PARAM_GROUP)
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

            getGroupList(id)
        }

        return view
    }

    private fun initView(view: View) {

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

        viewModel.getGroupImagesResponse()
            .observe(viewLifecycleOwner, Observer<ApiResponse> { apiResponse ->

                when (apiResponse.status) {

                    Status.LOADING -> rlProgress.visibility = View.VISIBLE

                    Status.SUCCESS -> {

                        rlProgress.visibility = View.GONE

                        val images = viewModel.getGroupImages(apiResponse.data!!)

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

    private fun setupView(groupImages: GroupImages) {

        tvName.text = group?.name
        tvDate.text = group?.date.toString()
        tvDescription.text = group?.shortDescription

        //Load image and save in cache
        Glide.with(this)
            .load(group?.imageUrl)
            .placeholder(R.mipmap.placeholder)
            .error(R.mipmap.placeholder)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(ivImage)
    }
}