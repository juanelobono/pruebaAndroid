package com.slashmobility.seleccionnexoandroid.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.slashmobility.seleccionnexoandroid.R
import com.slashmobility.seleccionnexoandroid.models.Group
import kotlinx.android.synthetic.main.item_group.view.*

class GroupsAdapter(private val groups : List<Group>, private val context: Context) : RecyclerView.Adapter<GroupsAdapter.GroupsView>(){

    private val datePattern : String = "dd-MM-yyyy"

    class GroupsView (view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.tvName!!
        val tvDescription = view.tvDescription!!
        val tvDate = view.tvDate!!
        val ivImage = view.ivImage!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsView {
        return GroupsView(LayoutInflater.from(context).inflate(R.layout.item_group, parent, false))
    }

    override fun getItemCount(): Int {
        return groups.size
    }

    override fun onBindViewHolder(holder: GroupsView, position: Int) {
        val group = groups.get(position)

        holder.tvName.text = group.name
        holder.tvDate.text = group.date.toString()
        holder.tvDescription.text = group.description

        //Load image and save in cache
        Glide.with(context)
            .load(group.imageUrl)
            .placeholder(R.mipmap.placeholder)
            .error(R.mipmap.placeholder)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.ivImage)
    }

}