package com.recam.screens.creditplanning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.recam.databinding.ActivityShowEmiBinding
import com.recam.databinding.ActivitySplashBinding
import com.recam.model.LoanApiUpdateResponse
import com.recam.model.LoginResponse
import com.recam.screens.home.HomeActivity
import com.recam.utils.Alert
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowEmiActivity : AppCompatActivity() {
    var showEmiBinding:ActivityShowEmiBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showEmiBinding= ActivityShowEmiBinding.inflate(LayoutInflater.from(this))
        setContentView(showEmiBinding!!.root)
         val intent= intent
        showEmiBinding!!.tvEmi.setText("Rs."+intent.getStringExtra(PreferenceConstent.emi))
        showEmiBinding!!.rateofinterest.setText(intent.getStringExtra(PreferenceConstent.interestrate))
        showEmiBinding!!.tvdays.setText(intent.getStringExtra(PreferenceConstent.interval))
        showEmiBinding!!.tvyear.setText(intent.getStringExtra(PreferenceConstent.year))
      //  callApiUpdateLoan()
        showEmiBinding!!.imgBack.setOnClickListener {
            finish()
        }
        showEmiBinding!!.btnSubmit.setOnClickListener {
            callApiUpdateLoan()
        }
    }

    private fun callApiUpdateLoan() {

        var loginUserData: LoginResponse.LoginUserData?= AppSheardPreference(this).getUser(PreferenceConstent.userData)
        val map: HashMap<String, String> = HashMap()
        map.put("id",AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.creditUserId))
        map.put("first_name","")
        map.put("last_name","")
        map.put("phone_no","")
        map.put("district","")
        map.put("gender","")
        map.put("age","")
        map.put("village","")
        map.put("panchayat","")
        map.put("block","")
        map.put("SHG_name","")
        map.put("LandHolding","")
        map.put("Holding_Area_in_Decimal",AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.land))
        map.put("credit_required_for",AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.loanrequiredfor))
        map.put("Total_Investment","")
        map.put("Own_Investment",intent.getStringExtra(PreferenceConstent.yourinvesment).toString())
        map.put("Credit_Required","")
        map.put("tenure",intent.getStringExtra(PreferenceConstent.year).toString())
        map.put("interest_rate",intent.getStringExtra(PreferenceConstent.interestrate).toString())
        map.put("installment_interval",intent.getStringExtra(PreferenceConstent.interval).toString())
        map.put("emi",intent.getStringExtra(PreferenceConstent.emi).toString())

        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        val callApi= apiInterface.callApiloanUpdate("Bearer " + loginUserData!!.token,map)
        callApi.enqueue(object : Callback<LoanApiUpdateResponse> {
            override fun onResponse(call: Call<LoanApiUpdateResponse>, response: Response<LoanApiUpdateResponse>) {
                customProgress.hideProgress()
                if (response.isSuccessful) {
                     if (response.body()!!.status == 200) {
                         Alert.showalertToGoHomePage(this@ShowEmiActivity,response!!.body()!!.message)
                      }
                    //else
                    //Toast.makeText(this@CreditCalculatorForActivity, response!!.body()!!.message, Toast.LENGTH_LONG).show()

                }else
                    Toast.makeText(this@ShowEmiActivity, "Something wrong. Try later", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<LoanApiUpdateResponse>, t: Throwable) {
                customProgress.hideProgress()
            }
        })
    }
}