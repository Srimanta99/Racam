package com.recam.model

data class LoginResponse(val success:String, val data:LoginUserData,val status:Int) {
    data class LoginUserData(val id:Int,val name:String,val email:String,val profile_photo_path:String,val usertype:String,
    val phone:String,val token:String,val profile_photo_url:String){

    }
}