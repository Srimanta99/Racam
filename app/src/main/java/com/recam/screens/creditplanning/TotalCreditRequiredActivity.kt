package com.recam.screens.creditplanning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.recam.R
import com.recam.databinding.ActivityTotalCreditRequiredBinding

class TotalCreditRequiredActivity : AppCompatActivity() {
    var activityTotalCreditRequiredBinding:ActivityTotalCreditRequiredBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTotalCreditRequiredBinding= ActivityTotalCreditRequiredBinding.inflate(LayoutInflater.from(this))
        setContentView(activityTotalCreditRequiredBinding!!.root)
    }
}