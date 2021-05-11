package com.recam.screens.creditplanning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.recam.R
import com.recam.databinding.ActivityCreditCalculatorLandBinding

class CreditCalculatorLandActivity : AppCompatActivity() {
    var creditCalculatorLandBinding:ActivityCreditCalculatorLandBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        creditCalculatorLandBinding= ActivityCreditCalculatorLandBinding.inflate(LayoutInflater.from(this))
        setContentView(creditCalculatorLandBinding!!.root)
        creditCalculatorLandBinding!!.btnNext.setOnClickListener {
            startActivity(Intent(this,CreditLoanRequiredActivity::class.java))

        }

        creditCalculatorLandBinding!!.imgBack.setOnClickListener {
            finish()
        }
    }
}