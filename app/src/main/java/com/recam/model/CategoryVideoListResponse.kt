package com.recam.model

data class CategoryVideoListResponse(val message:String,val data:ArrayList<Videodata>,val status:Int) {
    data class Videodata(val id:Int,val video_description:String,val video_title:String,val category:String,val language:String,
    val views:Int,val likes:Int,val video_url:String,val video_thumb:String,val created_at:String,val updated_at:String){

    }
}