package com.recam.model

data class DistrictApiresponseModel(val message:String,val data:ArrayList<DistList>, val status:Int) {
    data class DistList(val id:Int,val district_name:String){

    }
}