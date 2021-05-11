package com.recam.model

data class LoanEmiApiResponse(val message:String,val status:Int,val data:EmiData) {
    data class EmiData(val emi:Int,val interest_rate:String,val tenure:String,val  installment_interval:String){

    }
}