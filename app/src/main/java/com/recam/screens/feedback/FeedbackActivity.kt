package com.recam.screens.feedback

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.recam.databinding.ActivityFeedbackBinding

import com.recam.model.FeedbackSubmitResponse
import com.recam.model.LoginResponse
import com.recam.screens.home.HomeActivity
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedbackActivity : AppCompatActivity() {
    var activityFeedbackBinding: ActivityFeedbackBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFeedbackBinding= ActivityFeedbackBinding.inflate(LayoutInflater.from(this))
        setContentView(activityFeedbackBinding!!.root)
        activityFeedbackBinding!!.imgBack.setOnClickListener {
            finish()
        }

        activityFeedbackBinding!!.btnFeedback.setOnClickListener {
            if (!activityFeedbackBinding!!.etFeedback!!.text.toString().equals("")){
                callApiforfeedback()
            }else
                Toast.makeText(this@FeedbackActivity,"कृपया कुछ प्रतिक्रिया दर्ज करें",Toast.LENGTH_LONG).show()
        }
    }

    private fun callApiforfeedback() {
        var loginUserData: LoginResponse.LoginUserData?=AppSheardPreference(this).getUser(PreferenceConstent.userData)
        val map: HashMap<String, String> = HashMap()
        map.put("feedback", activityFeedbackBinding!!.etFeedback!!.text.toString())

        //  map["name"] = activityRegistrationBinding!!.etName.text.toString()
        // map["phone"] = activityRegistrationBinding!!.etPnumber.text.toString()
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        val callApi= apiInterface.callfeedbackApi("Bearer "+loginUserData!!.token,map )
        callApi.enqueue(object : Callback<FeedbackSubmitResponse> {
            override fun onResponse(call: Call<FeedbackSubmitResponse>, response: Response<FeedbackSubmitResponse>) {
                customProgress.hideProgress()
                if (response.isSuccessful){
                 if(response.body()!!.status==200){
                     Toast.makeText(this@FeedbackActivity,response!!.body()!!.message,Toast.LENGTH_LONG).show()
                 }else
                     Toast.makeText(this@FeedbackActivity,response!!.body()!!.message,Toast.LENGTH_LONG).show()

                }else
                    Toast.makeText(this@FeedbackActivity,"Something wrong. Try later",Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<FeedbackSubmitResponse>, t: Throwable) {
                customProgress.hideProgress()
            }
        })

    }
}