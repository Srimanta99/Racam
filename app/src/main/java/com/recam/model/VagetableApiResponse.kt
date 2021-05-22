package com.recam.model

data class VagetableApiResponse(val message:String,val Vegetable:Int,val Orchard:Int,val data:ArrayList<Vagetabledata>,val status:Int) {
    data class Vagetabledata(val id:Int,val crop_name:String, val crop_name_hindi:String,val Field_Preparation:String,val Nursery_and_Planting:String,
                             val Weeding:String,val Plant_Protection:String,val Fertilizers:String,val Wages:String,val Storage_transport_Others:String,val Own:String,
    val Hybrid:String, val High_Yield:String,val Total:String,val Yield:String,val Min_Price:String,val Market_Price_Range:String){

    }
}