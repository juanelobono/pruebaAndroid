package com.slashmobility.seleccionnexoandroid.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slashmobility.seleccionnexoandroid.R
import com.slashmobility.seleccionnexoandroid.extensions.loadImage
import com.slashmobility.seleccionnexoandroid.models.Group
import com.slashmobility.seleccionnexoandroid.utils.DateUtils
import kotlinx.android.synthetic.main.item_group.view.*

class GroupsAdapter(private val groups : List<Group>,
                    private val context: Context):
    RecyclerView.Adapter<GroupsAdapter.GroupsView>(){

    var onItemClick: ((Group) -> Unit)? = null

    inner class GroupsView (view: View): RecyclerView.ViewHolder(view) {
        val tvName = view.tvName!!
        val tvDescription = view.tvDescription!!
        val tvDate = view.tvDate!!
        val ivImage = view.ivImage!!

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(groups[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsView {
        return GroupsView(LayoutInflater.from(context).inflate(R.layout.item_group, parent, false))
    }

    override fun getItemCount(): Int {
        return groups.size
    }

    override fun onBindViewHolder(holder: GroupsView, position: Int) {
        val group = groups[position]

        holder.tvName.text = group.name
        holder.tvDate.text = group?.date?.let { DateUtils.getDateTime(it) }
        holder.tvDescription.text = group.shortDescription

        //Load image and save in cache
        holder.ivImage.loadImage(group.imageUrl)
    }

}