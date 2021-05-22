package com.recam.screens.creditplanning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.recam.R
import com.recam.databinding.ActivityCreditVagetableBinding

import com.recam.model.LoanApiUpdateResponse
import com.recam.model.LoginResponse
import com.recam.utils.Alert
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TotalLoanActivity : AppCompatActivity() {
    var acCreditVagetableActivity: ActivityCreditVagetableBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        acCreditVagetableActivity= ActivityCreditVagetableBinding.inflate(LayoutInflater.from(this))
        setContentView(acCreditVagetableActivity!!.root)
        acCreditVagetableActivity!!.btnNext.setOnClickListener {
            checkvalidation();
        }
        acCreditVagetableActivity!!.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun checkvalidation() {
         if(acCreditVagetableActivity!!.etTotalcredit.text.toString().equals("")) {
            Toast.makeText(this@TotalLoanActivity, "Enter total credit", Toast.LENGTH_LONG).show()
            return
        }
        else if (acCreditVagetableActivity!!.etYourinvesment.text.toString().equals("")){
            Toast.makeText(this@TotalLoanActivity, "Enter your investment", Toast.LENGTH_LONG).show()
            return
        }
      else {
            val totalCredit=(acCreditVagetableActivity!!.etTotalcredit.text.toString()).toInt()
            val yourinvestment=(acCreditVagetableActivity!!.etYourinvesment.text.toString()).toInt()
              acCreditVagetableActivity!!.tvLoanamount.setText((totalCredit-yourinvestment).toString())
            // callApiUpdateLoan()
             val intent= Intent(this,CreditEmiCalculatorActivity::class.java)
             intent.putExtra("yourinvestment",acCreditVagetableActivity!!.etYourinvesment.text.toString())
             intent.putExtra("loanrequired",acCreditVagetableActivity!!.tvLoanamount.text.toString())
             startActivity(intent)
        }

    }

    private fun callApiUpdateLoan() {

        var loginUserData: LoginResponse.LoginUserData?= AppSheardPreference(this).getUser(PreferenceConstent.userData)
        val map: HashMap<String, String> = HashMap()
        map.put("id", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.creditUserId))
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
        map.put("Holding_Area_in_Decimal", "")
        map.put("credit_required_for", acCreditVagetableActivity!!.tvLoanamount.text.toString())
        map.put("Total_Investment","")
        map.put("Own_Investment",acCreditVagetableActivity!!.etYourinvesment.text.toString())
        map.put("Credit_Required",acCreditVagetableActivity!!.etTotalcredit.text.toString())
        map.put("tenure","")
        map.put("interest_rate","")
        map.put("installment_interval","")
        map.put("emi","")

        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        val callApi= apiInterface.callApiloanUpdate("Bearer " + loginUserData!!.token,map)
        callApi.enqueue(object : Callback<LoanApiUpdateResponse> {
            override fun onResponse(call: Call<LoanApiUpdateResponse>, response: Response<LoanApiUpdateResponse>) {
                customProgress.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        Alert.showalertToGoHomePage(this@TotalLoanActivity,response!!.body()!!.message)
                    }
                    //else
                    //Toast.makeText(this@CreditCalculatorForActivity, response!!.body()!!.message, Toast.LENGTH_LONG).show()

                }else
                    Toast.makeText(this@TotalLoanActivity, "Something wrong. Try later", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<LoanApiUpdateResponse>, t: Throwable) {
                customProgress.hideProgress()
            }
        })
    }
}