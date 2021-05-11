package com.recam.screens.creditplanning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.recam.R
import com.recam.adapter.CustomSpinnerInstallmentRate
import com.recam.adapter.CustomSpinnerInteresrRate
import com.recam.adapter.CustomSpinnerLoanRequired
import com.recam.databinding.ActivityCreditEmiCalculatorBinding
import com.recam.model.DistrictApiresponseModel
import com.recam.model.LoanEmiApiResponse
import com.recam.model.LoginResponse
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreditEmiCalculatorActivity : AppCompatActivity() {
    var activityCreditEmiCalculatorBinding:ActivityCreditEmiCalculatorBinding?=null
    val loaninterest: MutableList<String> = ArrayList()
    val loanIntervalDays: MutableList<String> = ArrayList()
    var customspiinnerinterest: CustomSpinnerInteresrRate?=null
    var customSpinnerInstallmentRate: CustomSpinnerInstallmentRate?=null
    var seletedInterestrate=""
    var selectedloanInterval=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCreditEmiCalculatorBinding=ActivityCreditEmiCalculatorBinding.inflate(LayoutInflater.from(this))
        setContentView(activityCreditEmiCalculatorBinding!!.root)
        loaninterest.add("अपना विकल्प चुनें")
        loaninterest.add("02%")
        loaninterest.add("03%")
        loaninterest.add("04%")
        loaninterest.add("05%")
        loaninterest.add("06%")
        loaninterest.add("07%")
        loaninterest.add("08%")
        loaninterest.add("09%")
        loaninterest.add("10%")
        customspiinnerinterest= CustomSpinnerInteresrRate(this,R.layout.item_credit,loaninterest!!)
        activityCreditEmiCalculatorBinding!!.spinnerLoanIntrate.adapter=customspiinnerinterest
        activityCreditEmiCalculatorBinding!!.spinnerLoanIntrate.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                seletedInterestrate=loaninterest.get(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        loanIntervalDays.add("अपना विकल्प चुनें")
        loanIntervalDays.add("07 days")
        loanIntervalDays.add("15 days")
        loanIntervalDays.add("30 days")
        customSpinnerInstallmentRate= CustomSpinnerInstallmentRate(this,R.layout.item_credit,loanIntervalDays!!)
        activityCreditEmiCalculatorBinding!!.spinnerLoanInstalmnt.adapter=customSpinnerInstallmentRate
        activityCreditEmiCalculatorBinding!!.spinnerLoanInstalmnt.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedloanInterval=loanIntervalDays.get(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        activityCreditEmiCalculatorBinding!!.btnNext.setOnClickListener {
            callApifroEmi()
        }
        activityCreditEmiCalculatorBinding!!.imgBack.setOnClickListener {
            finish()
        }

    }

    private fun callApifroEmi() {
        var loginUserData: LoginResponse.LoginUserData?= AppSheardPreference(this).getUser(PreferenceConstent.userData)
        val map: HashMap<String, String> = HashMap()
        map.put("loan_amount",activityCreditEmiCalculatorBinding!!.etLoanrequired.text.toString())
        map.put("tenure",activityCreditEmiCalculatorBinding!!.etTernatureyear.text.toString())
        map.put("interest_rate",seletedInterestrate.replace("%",""))
        map.put("installment_interval",selectedloanInterval.replace(" days",""))
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        val callApi= apiInterface.callloanApploanemi("Bearer " + loginUserData!!.token,map)
        callApi.enqueue(object : Callback<LoanEmiApiResponse> {
            override fun onResponse(call: Call<LoanEmiApiResponse>, response: Response<LoanEmiApiResponse>) {
                customProgress.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                       var intent=Intent(this@CreditEmiCalculatorActivity,ShowEmiActivity::class.java)
                        intent.putExtra(PreferenceConstent.emi,response!!.body()!!.data.emi.toString())
                        intent.putExtra(PreferenceConstent.year,response!!.body()!!.data.tenure)
                        intent.putExtra(PreferenceConstent.interestrate,response!!.body()!!.data.interest_rate)
                        intent.putExtra(PreferenceConstent.interval,response!!.body()!!.data.installment_interval)
                        startActivity(intent)
                    }
                    //else
                    //Toast.makeText(this@CreditCalculatorForActivity, response!!.body()!!.message, Toast.LENGTH_LONG).show()

                }else
                    Toast.makeText(this@CreditEmiCalculatorActivity, "Something wrong. Try later", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<LoanEmiApiResponse>, t: Throwable) {
                customProgress.hideProgress()
            }
        })
    }
}