package com.recam.model

data class LoanCategoryApiResponse(val message:String,val status:Int,val data:ArrayList<LoanData>,val Vegetable:Int,val Orchard:Int,val parentName:String) {
    data class LoanData(val id:Int,val category_name:String,val category_name_hindi:String,val category_parent:Int,val cost_per_hectare:String)
}