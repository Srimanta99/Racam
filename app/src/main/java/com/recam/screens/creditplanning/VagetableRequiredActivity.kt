package com.recam.screens.creditplanning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.recam.R
import com.recam.adapter.CustomSpinnerLoanRequired
import com.recam.adapter.VagetableSpinnerAdapter
import com.recam.databinding.ActivityVagetableRequiredBinding
import com.recam.model.LoanEmiApiResponse
import com.recam.model.LoginResponse
import com.recam.model.VagetableApiResponse
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VagetableRequiredActivity : AppCompatActivity() {
    var vagetableRequiredBinding:ActivityVagetableRequiredBinding?=null
    var vatablelist: MutableList<String> = ArrayList()
    var VagetableSpinnerAdapter: VagetableSpinnerAdapter?=null
    var vagetableList:ArrayList<VagetableApiResponse.Vagetabledata>?=null
    var selectedvagetable:VagetableApiResponse.Vagetabledata?=null
    var selectvegcategory:Boolean?=false
    var feildprepration:Int?=0
    var selectedcattolat:Int?=0
    var nurserplanning:Int?=0
    var plantpotection:Int?=0
    var wedding:Int?=0
    var fertilizer:Int?=0
    var wages:Int?=0
    var hybrid:Int?=0
    var own:Int?=0
    var high_eylid:Int?=0
    var others:Int?=0
    var selectedamounts=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vagetableRequiredBinding= ActivityVagetableRequiredBinding.inflate(LayoutInflater.from(this))
        setContentView(vagetableRequiredBinding!!.root)
     //   vatablelist.add("अपना विकल्प चुनें")
        callApiforvagetable()
        vagetableRequiredBinding!!.spinnerVagetable.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedvagetable=vagetableList!!.get(p2)
                 /*vagetableRequiredBinding!!.feildprepration.setText("Feild Prepration ")
                vagetableRequiredBinding!!.nurseryandplanting.setText("Nursery and Planting ")
                vagetableRequiredBinding!!.wedding.setText("Weeding ")
                vagetableRequiredBinding!!.plantpotection.setText("Plant Protection ")
                vagetableRequiredBinding!!.fertilizer.setText("Fertilizers ")
                vagetableRequiredBinding!!.wages.setText("Wages ")
                vagetableRequiredBinding!!.others.setText("Storage Transport Others ")*/

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        vagetableRequiredBinding!!.imgBack.setOnClickListener {
            finish()
        }
        vagetableRequiredBinding!!.btnNext.setOnClickListener {
            if(checkvalidation()!!){
                selectedcattolat=feildprepration!!+nurserplanning!!+wedding!!+plantpotection!!+fertilizer!!+wages!!+others!!+own!!+hybrid!!+high_eylid!!
                val intent=Intent(this,TotalCreditRequiredActivity::class.java)
                AppSheardPreference(this).setvalue_in_preference(PreferenceConstent.selectedVagetable,selectedamounts)
                intent.putExtra("loanamount",selectedcattolat.toString())
                startActivity(intent)
            }else
                Toast.makeText(this@VagetableRequiredActivity, "Select at-least one category", Toast.LENGTH_LONG).show()
           /* if (selectvegcategory!!) {
                selectedcattolat=feildprepration!!+nurserplanning!!
                startActivity(Intent(this, TotalCreditRequiredActivity::class.java))
            }
            else
                Toast.makeText(this@VagetableRequiredActivity, "Select at-least one category", Toast.LENGTH_LONG).show()
*/
        }
        /*vagetableRequiredBinding!!.feildprepration.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {

            }

        }
        vagetableRequiredBinding!!.nurseryandplanting.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {

            }
        }*/
    }

    private fun checkvalidation(): Boolean? {
        if (vagetableRequiredBinding!!.feildprepration.isChecked){
            selectvegcategory=true
            feildprepration = selectedvagetable!!.Field_Preparation.toInt()
            selectedamounts=selectedamounts+","+ selectedvagetable!!.Field_Preparation

        }
        if (vagetableRequiredBinding!!.nurseryandplanting.isChecked){
            selectvegcategory=true
            nurserplanning = selectedvagetable!!.Nursery_and_Planting.toInt()
            selectedamounts=selectedamounts+","+ selectedvagetable!!.Nursery_and_Planting
        }
         if (vagetableRequiredBinding!!.wedding.isChecked){
             selectvegcategory=true
             wedding=selectedvagetable!!.Weeding.toInt()
             selectedamounts=selectedamounts+","+ selectedvagetable!!.Weeding
        }
        if (vagetableRequiredBinding!!.plantpotection.isChecked){
            selectvegcategory=true
            plantpotection=selectedvagetable!!.Plant_Protection.toInt()
            selectedamounts=selectedamounts+","+ selectedvagetable!!.Plant_Protection
        }
        if (vagetableRequiredBinding!!.fertilizer.isChecked){
            selectvegcategory=true
            fertilizer=selectedvagetable!!.Fertilizers.toInt()
            selectedamounts=selectedamounts+","+ selectedvagetable!!.Plant_Protection
        }
        if (vagetableRequiredBinding!!.wages.isChecked){
            selectvegcategory=true
            wages=selectedvagetable!!.Wages.toInt()
            selectedamounts=selectedamounts+","+ selectedvagetable!!.Wages
        }
        if (vagetableRequiredBinding!!.own.isChecked){
            selectvegcategory=true
            own=selectedvagetable!!.Own.toInt()
            selectedamounts=selectedamounts+","+ selectedvagetable!!.Own
        }
        if (vagetableRequiredBinding!!.hybrid.isChecked){
            selectvegcategory=true
            hybrid=selectedvagetable!!.Hybrid.toInt()
            selectedamounts=selectedamounts+","+ selectedvagetable!!.Hybrid
        }
        if (vagetableRequiredBinding!!.highyelid.isChecked){
            selectvegcategory=true
            high_eylid=selectedvagetable!!.High_Yield.toInt()
            selectedamounts=selectedamounts+","+ selectedvagetable!!.High_Yield
        }

         if (vagetableRequiredBinding!!.others.isChecked){
            selectvegcategory=true
            others=selectedvagetable!!.Storage_transport_Others.toInt()
             selectedamounts=selectedamounts+","+ selectedvagetable!!.Storage_transport_Others
        }
        return selectvegcategory
    }

    private fun callApiforvagetable() {
        var loginUserData: LoginResponse.LoginUserData?= AppSheardPreference(this).getUser(PreferenceConstent.userData)
        val map: HashMap<String, String> = HashMap()
        map.put("category_id",AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.selectedVagetablecat))
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        val callApi= apiInterface.callloancategoryApivagetable("Bearer " + loginUserData!!.token,map)
        callApi.enqueue(object : Callback<VagetableApiResponse> {
            override fun onResponse(call: Call<VagetableApiResponse>, response: Response<VagetableApiResponse>) {
                customProgress.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        vagetableList=response!!.body()!!.data
                        for (i in 0 until response.body()!!.data.size){
                            vatablelist.add(response!!.body()!!.data.get(i).crop_name_hindi)
                        }
                        //vatablelist.add("अपना विकल्प चुनें")
                      //  selectedvagetable=vagetableList!!.get(p2)
                        VagetableSpinnerAdapter= VagetableSpinnerAdapter(this@VagetableRequiredActivity, R.layout.item_credit,vatablelist!!)
                        vagetableRequiredBinding!!.spinnerVagetable.adapter=VagetableSpinnerAdapter
                     /*   var intent= Intent(this@CreditEmiCalculatorActivity,ShowEmiActivity::class.java)
                        intent.putExtra(PreferenceConstent.emi,response!!.body()!!.data.emi.toString())
                        intent.putExtra(PreferenceConstent.year,response!!.body()!!.data.tenure)
                        intent.putExtra(PreferenceConstent.interestrate,response!!.body()!!.data.interest_rate)
                        intent.putExtra(PreferenceConstent.interval,response!!.body()!!.data.installment_interval)
                        intent.putExtra(PreferenceConstent.yourinvesment,activityCreditEmiCalculatorBinding!!.etYourinvesment.text.toString())
                        startActivity(intent)*/
                    }
                    //else
                    //Toast.makeText(this@CreditCalculatorForActivity, response!!.body()!!.message, Toast.LENGTH_LONG).show()

                }else
                    Toast.makeText(this@VagetableRequiredActivity, "Something wrong. Try later", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<VagetableApiResponse>, t: Throwable) {
                customProgress.hideProgress()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        selectvegcategory=false
        feildprepration=0;
        nurserplanning=0
        wedding=0
        plantpotection=0
        fertilizer=0
        wages=0
        others=0
        own=0
        high_eylid=0
        hybrid=0

    }
}