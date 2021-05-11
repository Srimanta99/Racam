package com.recam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.recam.R
import com.recam.databinding.ItemCategoryBinding
import com.recam.databinding.ItemSideMenuBinding
import com.recam.databinding.ItemVideoBinding
import com.recam.model.CategoryApiResponse
import com.recam.model.CategoryVideoListResponse
import com.recam.screens.categoryvideolist.CategoryVideoList
import com.recam.screens.home.HomeActivity
import com.recam.utils.OnitemClickInterface

class CategoryVideoListAdapter(
    val activity: CategoryVideoList,
    val videolist: ArrayList<CategoryVideoListResponse.Videodata>,
    val onitemClickInterface: OnitemClickInterface): RecyclerView.Adapter<CategoryVideoListAdapter.ViewHolder>() {
    var  itemView: ItemVideoBinding?=null
    class ViewHolder(val  itemView: ItemVideoBinding) : RecyclerView.ViewHolder(itemView!!.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVideoBinding.inflate(inflater)
        itemView=binding
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemView!!.catname.setText(videolist.get(position).video_title)
        itemView!!.catImage.setOnClickListener {
            onitemClickInterface.ItemClick(position)
        }

        //val aUrl: String = videolist.get(position).video_url.replace("http", "https")
        Glide.with(activity)
            .load(videolist.get(position).video_url)
            .placeholder(R.drawable.ic_placeholder)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
            .into(itemView!!.catImage);

       /* itemView!!.tvCreated.setTypeface(CustomTypeface.getRajdhaniMedium(activity!!))
        itemView!!.tvCreatedDate.setTypeface(CustomTypeface.getRajdhaniMedium(activity!!))
        itemView!!.tvsitename.setTypeface(CustomTypeface.getRajdhaniSemiBold(activity!!))
        itemView!!.tvUsercount.setTypeface(CustomTypeface.getRajdhaniMedium(activity!!))
        itemView!!.tvSitecount.setTypeface(CustomTypeface.getRajdhaniMedium(activity!!))*/


    }

    override fun getItemCount(): Int {
        return  videolist.size;
    }

}