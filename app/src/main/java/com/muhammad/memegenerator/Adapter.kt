package com.muhammad.memegenerator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class Adapter(private var list:ArrayList<MemeDetails>)
    : RecyclerView.Adapter<Adapter.MemeViewHolder>() {

    class MemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var meme: ImageView = itemView.findViewById(R.id.meme)
            var name: TextView = itemView.findViewById(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        val view:View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return MemeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        val meme:MemeDetails = list[position]
        holder.name.text = meme.name
// Using Glide with placeholder & error handling
        Glide.with(holder.itemView.context)
            .load(meme.url)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.settings) // Set a placeholder image
                    .error(R.drawable.settings) // Set an error image if loading fails
            ).into(holder.meme)
    }
    // Function to update the list dynamically
    fun updateData(newList: ArrayList<MemeDetails>) {
        list = newList
        notifyDataSetChanged() // Notify adapter about the changes
    }
}