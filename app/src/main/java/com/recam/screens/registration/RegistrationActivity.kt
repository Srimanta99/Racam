package com.recam.screens.registration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.recam.databinding.ActivityRegistrationBinding
import com.recam.model.RegistrationResponseModel
import com.recam.screens.login.LoginActivity
import com.recam.screens.otp.OtpActivity
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {
    var activityRegistrationBinding:ActivityRegistrationBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegistrationBinding= ActivityRegistrationBinding.inflate(LayoutInflater.from(this))
        setContentView(activityRegistrationBinding!!.root)
        activityRegistrationBinding!!.tvLoginNow.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))

        }
        activityRegistrationBinding!!.rlSignup.setOnClickListener {
           /* startActivity(Intent(this, OtpActivity::class.java))
            finish()*/
            if (!activityRegistrationBinding!!.etName.text.toString().equals("")){
                if (!activityRegistrationBinding!!.etPnumber.text.toString().equals("")){
                    callApiforReg()
                }else
                    Toast.makeText(this,"कृपया फ़ोन नंबर दर्ज करें", Toast.LENGTH_LONG).show()
            }else
                Toast.makeText(this,"कृपया नाम दर्ज करें", Toast.LENGTH_LONG).show()
        }
    }

    private fun callApiforReg() {
        val map: HashMap<String, String> = HashMap()
        map.put("name", activityRegistrationBinding!!.etName.text.toString())
        map.put("phone", activityRegistrationBinding!!.etPnumber.text.toString())
      //  map["name"] = activityRegistrationBinding!!.etName.text.toString()
       // map["phone"] = activityRegistrationBinding!!.etPnumber.text.toString()
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        val callApi= apiInterface.callRegistrationApi(map )
        callApi.enqueue(object : Callback<RegistrationResponseModel> {
            override fun onResponse(call: Call<RegistrationResponseModel>, response: Response<RegistrationResponseModel>) {
                customProgress.hideProgress()
                if (response.isSuccessful){

                    if (response.body()!!.status==200){

                        AppSheardPreference(this@RegistrationActivity).setvalue_in_preference(PreferenceConstent.login_phone,activityRegistrationBinding!!.etPnumber.text.toString())
                        startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
                        finish()
                    }else
                    Toast.makeText(this@RegistrationActivity,response.message(),Toast.LENGTH_LONG).show()
                }
                else
                    Toast.makeText(this@RegistrationActivity,"Something wrong. Try later",Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<RegistrationResponseModel>, t: Throwable) {
                customProgress.hideProgress()
            }
        })
    }


}