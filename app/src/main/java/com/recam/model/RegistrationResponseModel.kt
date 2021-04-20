package com.recam.model

data class RegistrationResponseModel(val success:String, val status:Int,val data:Userdata) {
    data class Userdata(val name:String,val phone:String,val id:Int){

    }
}