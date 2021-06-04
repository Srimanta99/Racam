package com.recam.screens.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.recam.R
import com.recam.databinding.ActivityLoginBinding
import com.recam.model.OtpResponse
import com.recam.screens.home.HomeActivity
import com.recam.screens.otp.OtpActivity
import com.recam.screens.registration.RegistrationActivity
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

class LoginActivity : AppCompatActivity() {
    var loginBinding:ActivityLoginBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding= ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(loginBinding!!.root)
        loginBinding!!.rlSignin.setOnClickListener {
         //   startActivity(Intent(this,OtpActivity::class.java))
          //  finish()
            if (!loginBinding!!.etPhonenumber.text.toString().equals("")){
                if(Utils.isNetworkConnected(this))
                   callAPiforOtp()
                else
                    Toast.makeText(this,"No Internet connection",Toast.LENGTH_LONG).show()
            }else
                Toast.makeText(this,"कृपया फ़ोन नंबर दर्ज करें",Toast.LENGTH_LONG).show()
        }
        loginBinding!!.tvRegisternow.setOnClickListener {
            startActivity(Intent(this,RegistrationActivity::class.java))
        }
    }

    private fun callAPiforOtp() {
        val map: HashMap<String, String> = HashMap()
        map.put("phone",loginBinding!!.etPhonenumber.text.toString())
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
       val callApi= apiInterface.callgrtOtp(map)
        callApi.enqueue(object :Callback<OtpResponse>{
            override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
                customProgress.hideProgress()
                if (response.isSuccessful){
                   // Log.d("otp",response.body()!!.string())
                    if (response.body()!!.status==200) {
                        AppSheardPreference(this@LoginActivity).setvalue_in_preference(PreferenceConstent.login_phone, loginBinding!!.etPhonenumber.text.toString())
                        AppSheardPreference(this@LoginActivity).setvalue_in_preference(PreferenceConstent.otp,response.body()!!.otp)
                        startActivity(Intent(this@LoginActivity, OtpActivity::class.java))
                        finish()
                    }else
                        Toast.makeText(this@LoginActivity,response.message(),Toast.LENGTH_LONG).show()
                }else
                    Toast.makeText(this@LoginActivity,"This number is not register",Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                customProgress.hideProgress()
            }
        })
    }
}