package com.recam.model

data class HomeApiResponse(val message:String,val data:Data, val status:Int) {
    data class Data(val category:ArrayList<CategoryData>,val latest_update:ArrayList<LateastUpdate>){
    }

    data class LateastUpdate(val id:Int,val video_description:String,val video_title:String,val category:String,val language:String,
    val views:Int, val likes:Int,val video_url:String,val video_thumb:String,val created_at:String,val updated_at:String){

    }
    data class CategoryData(val id:Int, val name:String,val language:String,val description:String,val parent_id:String,val is_active:String,
    val created_at:String,val updated_at:String, val videos:ArrayList<CategoryVideo>){
    }
    data class CategoryVideo(val id: Int,val video_description:String,val video_title:String,val category:String,val language:String,
    val views:Int,val likes:Int,val video_url:String,val video_thumb:String,val created_at:String,val  updated_at:String){

    }
}