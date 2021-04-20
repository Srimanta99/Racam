package com.recam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recam.databinding.ItemSideMenuBinding
import com.recam.model.CategoryApiResponse
import com.recam.screens.home.HomeActivity
import com.recam.utils.OnitemClickInterface

class CategoryAdapter(
    val activity: HomeActivity,
    val categorylist: ArrayList<CategoryApiResponse.CategoryData>,
    val onitemClickInterface: OnitemClickInterface): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    var  itemView: ItemSideMenuBinding?=null
    class ViewHolder(val  itemView: ItemSideMenuBinding) : RecyclerView.ViewHolder(itemView!!.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSideMenuBinding.inflate(inflater)
        itemView=binding
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemView!!.tvItemname!!.setText(categorylist.get(position).name)
        itemView!!.tvItemname.setOnClickListener {
            onitemClickInterface.ItemClick(position)
        }

       /* itemView!!.tvCreated.setTypeface(CustomTypeface.getRajdhaniMedium(activity!!))
        itemView!!.tvCreatedDate.setTypeface(CustomTypeface.getRajdhaniMedium(activity!!))
        itemView!!.tvsitename.setTypeface(CustomTypeface.getRajdhaniSemiBold(activity!!))
        itemView!!.tvUsercount.setTypeface(CustomTypeface.getRajdhaniMedium(activity!!))
        itemView!!.tvSitecount.setTypeface(CustomTypeface.getRajdhaniMedium(activity!!))*/


    }

    override fun getItemCount(): Int {
        return  categorylist.size;
    }

}