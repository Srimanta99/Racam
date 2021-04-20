package com.recam.screens.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.recam.R
import com.recam.databinding.ActivityProfileBinding
import com.recam.model.LoginResponse
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent

class ProfileActivity : AppCompatActivity() {
    var activityProfileBinding:ActivityProfileBinding?=null
    var loginUserData: LoginResponse.LoginUserData?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityProfileBinding= ActivityProfileBinding.inflate(LayoutInflater.from(this))
        setContentView(activityProfileBinding!!.root)
        loginUserData= AppSheardPreference(this).getUser(PreferenceConstent.userData)
        activityProfileBinding!!.tvusername.setText(loginUserData!!.name)
        activityProfileBinding!!.tvphone.setText(loginUserData!!.phone)
        activityProfileBinding!!.imgBack.setOnClickListener {
            finish()
        }
        Glide.with(this)
                .load(loginUserData!!.profile_photo_url)
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(activityProfileBinding!!.profileImage);
    }
}