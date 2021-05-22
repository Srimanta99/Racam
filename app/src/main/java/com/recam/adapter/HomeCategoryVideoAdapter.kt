package com.recam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.recam.R
import com.recam.databinding.ItemCategoryBinding
import com.recam.model.HomeApiResponse
import com.recam.screens.home.HomeActivity
import com.recam.utils.OnitemClickInterface
import kotlin.math.roundToInt

class HomeCategoryVideoAdapter(
    val activity: HomeActivity,
    val videos: ArrayList<HomeApiResponse.CategoryVideo>,
    val param: OnitemClickInterface,
    ): RecyclerView.Adapter<HomeCategoryVideoAdapter.ViewHolder>() {
    var  itemView: ItemCategoryBinding?=null
    class ViewHolder(val  itemView: ItemCategoryBinding) : RecyclerView.ViewHolder(itemView!!.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater)
        itemView=binding
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemView!!.itemname.setText(videos.get(position).video_description)

      //  val aUrl: String = videos.get(position).video_url.replace("http", "https")
        Glide.with(activity)
            .load(videos.get(position).video_url)
            .placeholder(R.drawable.ic_placeholder)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
            .override(activity.resources.getDimension(R.dimen._150sdp).roundToInt(),activity.resources.getDimension(R.dimen._90sdp).roundToInt())
            .into(itemView!!.catImage);

        itemView!!.rlItemVideo.setOnClickListener {
            param.ItemClick(position)
        }

       /* itemView!!.tvCreated.setTypeface(CustomTypeface.getRajdhaniMedium(activity!!))
        itemView!!.tvCreatedDate.setTypeface(CustomTypeface.getRajdhaniMedium(activity!!))
        itemView!!.tvsitename.setTypeface(CustomTypeface.getRajdhaniSemiBold(activity!!))
        itemView!!.tvUsercount.setTypeface(CustomTypeface.getRajdhaniMedium(activity!!))
        itemView!!.tvSitecount.setTypeface(CustomTypeface.getRajdhaniMedium(activity!!))*/


    }

    override fun getItemCount(): Int {
        return  videos.size;
    }

}