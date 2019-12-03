package com.slashmobility.seleccionnexoandroid.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.slashmobility.seleccionnexoandroid.R
import com.slashmobility.seleccionnexoandroid.extensions.loadImage
import kotlinx.android.synthetic.main.item_image.view.*

class ImageAdapter(private val context:Context, private val imagesUrl: ArrayList<String>?) : PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_image, collection, false) as ViewGroup

        setupView(view, imagesUrl!![position])
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

        ivImage.loadImage(urlImage)
    }
}