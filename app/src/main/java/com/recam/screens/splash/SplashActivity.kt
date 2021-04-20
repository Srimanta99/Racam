
package com.recam.screens.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.recam.R
import com.recam.screens.home.HomeActivity
import com.recam.screens.login.LoginActivity
import com.recam.utils.ApplicationConstant
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            if(AppSheardPreference(this).getvalue_in_preference(PreferenceConstent.iS_LOGIN).equals("1")){
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, ApplicationConstant.SPLASHTIME)
    }
}