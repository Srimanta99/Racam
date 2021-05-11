package com.recam.screens.profile

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.recam.R
import com.recam.databinding.ActivityProfileBinding
import com.recam.model.LoginResponse
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent


class ProfileActivity : AppCompatActivity() {
    var activityProfileBinding: ActivityProfileBinding?=null
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
        activityProfileBinding!!.btnSetpassword.setOnClickListener {
            setpopup()
        }
        Glide.with(this)
                .load(loginUserData!!.profile_photo_url)
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(activityProfileBinding!!.profileImage);
    }

    private fun setpopup() {
        val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        // Inflate a custom view using layout inflater
        val view = inflater.inflate(R.layout.set_password_pop_up_layout, null)
        val popupWindow = PopupWindow(
            view,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        popupWindow.width= resources.getDimension(R.dimen._250sdp).toInt()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }


        // If API level 23 or higher then execute the code
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // Create a new slide animation for popup window enter transition
            val slideIn = Slide()
            slideIn.slideEdge = Gravity.TOP
            popupWindow.enterTransition = slideIn
            // Slide animation for popup window exit transition
            val slideOut = Slide()
            slideOut.slideEdge = Gravity.RIGHT
            popupWindow.exitTransition = slideOut

        }
        val cross=view.findViewById<ImageView>(R.id.cross)
        val btnpass=view.findViewById<Button>(R.id.btn_pass)
        btnpass.setOnClickListener {
            Toast.makeText(this,"Under development",Toast.LENGTH_LONG).show()
        }
        cross.setOnClickListener {
            popupWindow.dismiss()
        }
      //  TransitionManager.beginDelayedTransition(this.findViewById(R.id.root_layout))
        popupWindow.setFocusable(true);
        popupWindow.update();
        popupWindow.showAsDropDown(
            activityProfileBinding!!.rlHeader, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )
    }
}