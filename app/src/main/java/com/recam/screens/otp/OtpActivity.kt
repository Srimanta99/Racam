package com.recam.screens.otp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.recam.databinding.ActivityOtpBinding
import com.recam.model.LoginResponse
import com.recam.model.RegistrationResponseModel
import com.recam.screens.home.HomeActivity
import com.recam.utils.Utils
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class OtpActivity : AppCompatActivity() {
    var activityOtpBinding:ActivityOtpBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityOtpBinding= ActivityOtpBinding.inflate(LayoutInflater.from(this))
        setContentView(activityOtpBinding!!.root)
        activityOtpBinding!!.etOtpNo.setText(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.otp))
        activityOtpBinding!!.rlOtp.setOnClickListener {
           // startActivity(Intent(this,HomeActivity::class.java))
          //  finish()
            if(!activityOtpBinding!!.etOtpNo.text.toString().equals("")){
                if(Utils.isNetworkConnected(this))
               callLoginApi()
                else
                Toast.makeText(this,"No Internet connection",Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(this,"कृपया ओटीपी दर्ज करें", Toast.LENGTH_LONG).show()
        }
    }

    private fun callLoginApi() {

            val map: HashMap<String, String> = HashMap()
            map.put("phone", AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.login_phone))
            map.put("otp",activityOtpBinding!!.etOtpNo.text.toString())
        //  map["name"] = activityRegistrationBinding!!.etName.text.toString()
            // map["phone"] = activityRegistrationBinding!!.etPnumber.text.toString()
            val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
            customProgress.showProgress(this, "Please Wait..", false)
            val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
            val callApi= apiInterface.callLoginApi(map )
            callApi.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    customProgress.hideProgress()
                    if (response.isSuccessful){
                        if (response.body()!!.status==200) {
                            startActivity(Intent(this@OtpActivity, HomeActivity::class.java))
                            AppSheardPreference(this@OtpActivity).setvalue_in_preference(PreferenceConstent.iS_LOGIN,"1")
                            AppSheardPreference(this@OtpActivity).setUserData(PreferenceConstent.userData,response!!.body()!!.data)
                            finish()
                        }
                        else{
                            Toast.makeText(this@OtpActivity,response.message(),Toast.LENGTH_LONG).show()
                        }
                    }else
                        Toast.makeText(this@OtpActivity, "ओटप गलत है",Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    customProgress.hideProgress()
                }
            })

    }


}