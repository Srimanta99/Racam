package com.recam.screens.creditplanning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.recam.R
import com.recam.adapter.CustomSpinnerAdapterLoanCategory
import com.recam.databinding.ActivityCreditCalculatorForBinding
import com.recam.model.LoanCategoryApiResponse
import com.recam.model.LoginResponse
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreditCalculatorForActivity : AppCompatActivity() {
    var activityCreditCalculatorForBinding:ActivityCreditCalculatorForBinding?=null
    var customSpinnerAdapter: CustomSpinnerAdapterLoanCategory?=null
    var cat_id="0"
    var parent_cat_id="0"
    var loancatList:ArrayList<LoanCategoryApiResponse.LoanData>?= ArrayList()
    var loanData:LoanCategoryApiResponse.LoanData?=null
    var pagecount:Int=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCreditCalculatorForBinding= ActivityCreditCalculatorForBinding.inflate(LayoutInflater.from(this))
        setContentView(activityCreditCalculatorForBinding!!.root)
        activityCreditCalculatorForBinding!!.spinnerLoadCat.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2>0) {
                    cat_id = loancatList!!.get(p2).id.toString()
                    parent_cat_id=loancatList!!.get(p2).category_parent.toString()
                }

              //  callLoanCategoryApi(cat_id)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        callLoanCategoryApi(cat_id)
        loanData= LoanCategoryApiResponse.LoanData(0,"","अपना विकल्प चुनें",0)
        loancatList!!.add(loanData!!)
        customSpinnerAdapter= CustomSpinnerAdapterLoanCategory(this@CreditCalculatorForActivity,R.layout.item_credit,loancatList!!)
        activityCreditCalculatorForBinding!!.spinnerLoadCat.adapter=customSpinnerAdapter;
       activityCreditCalculatorForBinding!!.imgBack.setOnClickListener {
           pagecount--
           if(pagecount>=1){
               callLoanCategoryApi(parent_cat_id)
           }
           else if(pagecount==0)
               finish()
       }

        activityCreditCalculatorForBinding!!.btnNext.setOnClickListener {
            if (!cat_id.equals("0")) {
                pagecount++
                callLoanCategoryApi(cat_id)
            }
            else
                Toast.makeText(this@CreditCalculatorForActivity,"अपना विकल्प चुनें",Toast.LENGTH_LONG).show()
        }
    }

    private fun callLoanCategoryApi(cat_id: String) {
        var loginUserData: LoginResponse.LoginUserData?= AppSheardPreference(this).getUser(PreferenceConstent.userData)
        val map: HashMap<String, String> = HashMap()
        map.put("category_id", cat_id)
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
         customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        val callApi= apiInterface.callloancategoryApi("Bearer " + loginUserData!!.token, map)
        callApi.enqueue(object : Callback<LoanCategoryApiResponse> {
            override fun onResponse(call: Call<LoanCategoryApiResponse>, response: Response<LoanCategoryApiResponse>) {
                customProgress.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                            loancatList!!.clear()
                            loancatList!!.add(loanData!!)
                           for (i in 0 until response!!.body()!!.data.size){
                               loancatList!!.add(response!!.body()!!.data.get(i))
                           }
                            customSpinnerAdapter= CustomSpinnerAdapterLoanCategory(this@CreditCalculatorForActivity,R.layout.item_credit,loancatList!!)
                           activityCreditCalculatorForBinding!!.spinnerLoadCat.adapter=customSpinnerAdapter;
                    }
                    //else
                       //Toast.makeText(this@CreditCalculatorForActivity, response!!.body()!!.message, Toast.LENGTH_LONG).show()

                }else
                  startActivity(Intent(this@CreditCalculatorForActivity,CreditCalculatorLandActivity::class.java))
                //else
                    //Toast.makeText(this@CreditCalculatorForActivity, "Something wrong. Try later", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<LoanCategoryApiResponse>, t: Throwable) {
                 customProgress.hideProgress()
            }
        })

    }

  /*  override fun onBackPressed() {
        super.onBackPressed()
        if(!parent_cat_id.equals("0"))
        callLoanCategoryApi(parent_cat_id)
    }*/


}