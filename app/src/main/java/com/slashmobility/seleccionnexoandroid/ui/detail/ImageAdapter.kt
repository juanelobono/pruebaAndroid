package com.slashmobility.seleccionnexoandroid.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.slashmobility.seleccionnexoandroid.R
import kotlinx.android.synthetic.main.item_image.view.*

class ImageAdapter(private val context:Context, private val imagesUrl: ArrayList<String>?) : PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_image, collection, false) as ViewGroup

        setupView(view, imagesUrl!!.get(position))
        collection.addView(view)

        return view
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return imagesUrl!!.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    private fun setupView(view: View, urlImage : String){
        val ivImage = view.ivImage

        Glide.with(context)
            .load(urlImage)
            .placeholder(R.mipmap.placeholder)
            .error(R.mipmap.placeholder)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(ivImage)
    }
}