package com.recam.screens.creditplanning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.recam.R
import com.recam.databinding.ActivityCreditCalculatorLandBinding
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent

class CreditCalculatorLandActivity : AppCompatActivity() {
    var creditCalculatorLandBinding:ActivityCreditCalculatorLandBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        creditCalculatorLandBinding= ActivityCreditCalculatorLandBinding.inflate(LayoutInflater.from(this))
        setContentView(creditCalculatorLandBinding!!.root)
        creditCalculatorLandBinding!!.btnNext.setOnClickListener {
            if (!creditCalculatorLandBinding!!.etLand.text.toString().equals("")) {
                AppSheardPreference(this).setvalue_in_preference(PreferenceConstent.land,creditCalculatorLandBinding!!.etLand.text.toString())
                AppSheardPreference(this).setvalue_in_preference(PreferenceConstent.landamount,(creditCalculatorLandBinding!!.etLand.text.toString().toInt()*0.0004).toString())
                startActivity(Intent(this, VagetableRequiredActivity::class.java))
            }else
                Toast.makeText(this,"Enter land area",Toast.LENGTH_LONG).show()

        }

        creditCalculatorLandBinding!!.imgBack.setOnClickListener {
            finish()
        }
    }
}