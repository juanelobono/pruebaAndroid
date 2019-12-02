package com.slashmobility.seleccionnexoandroid.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.slashmobility.seleccionnexoandroid.R
import kotlinx.android.synthetic.main.item_group.view.*

class GroupsAdapter(val groups : ArrayList<String>, private val context: Context) : RecyclerView.Adapter<GroupsAdapter.GroupsView>(){

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
        Group group = groups.get(position)

        holder.tvName.text =group.name
        holder.tvDate.text =group.date
        holder.tvDescription.text =group.descrption

        Glide.with(context)
            .load(group.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .skipMemoryCache(true) //2
            .diskCacheStrategy(DiskCacheStrategy.ALL) //3
            .into(holder.ivImage)

    }

}