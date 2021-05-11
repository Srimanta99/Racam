package com.recam.model

data class VideoLikeResponse(val message:String, val status:Int, val data:ArrayList<VideoData>) {
    data class VideoData(val id:Int,val video_description:String,val video_title:String, val category:String,val language:String,
    val views:Int,val likes:String,val video_url:String,val video_thumb:String,val created_at:String,val updated_at:String){

    }
}