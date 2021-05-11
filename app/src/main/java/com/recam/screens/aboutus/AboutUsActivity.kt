package com.recam.screens.aboutus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.recam.R
import com.recam.databinding.ActivityAboutUsBinding

class AboutUsActivity : AppCompatActivity() {
    var activityAboutUsBinding:ActivityAboutUsBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAboutUsBinding= ActivityAboutUsBinding.inflate(LayoutInflater.from(this))
        setContentView(activityAboutUsBinding!!.root)
        activityAboutUsBinding!!.imgBack.setOnClickListener {
            finish()
        }

    }
}