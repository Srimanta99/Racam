package com.recam.model

data class CategoryApiResponse(val message:String,val data:ArrayList<CategoryData>,val status:Int) {
    data class CategoryData(val id:Int,val name:String,val language:String,val description:String,val parent_id:String,val is_active:String
    ,val created_at:String,val updated_at:String)
}