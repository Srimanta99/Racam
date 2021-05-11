package com.recam.screens.creditplanning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import com.recam.R
import com.recam.adapter.CustomSpinnerLoanRequired
import com.recam.databinding.ActivityCreditLoanRequiredBinding

class CreditLoanRequiredActivity : AppCompatActivity() {
    var activityCreditLoanRequiredBinding:ActivityCreditLoanRequiredBinding?=null
    val loanRequired: MutableList<String> = ArrayList()
    var customSpinnerLoanRequired: CustomSpinnerLoanRequired?=null
    var loanrequiredStr=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCreditLoanRequiredBinding= ActivityCreditLoanRequiredBinding.inflate(LayoutInflater.from(this))
        setContentView(activityCreditLoanRequiredBinding!!.root)
        activityCreditLoanRequiredBinding!!.imgBack.setOnClickListener {
            finish()
        }
        activityCreditLoanRequiredBinding!!.btnNext.setOnClickListener {
            startActivity(Intent(this, CreditEmiCalculatorActivity::class.java))

        }
        loanRequired.add("अपना विकल्प चुनें")
        loanRequired.add("Field Preparation")
        loanRequired.add("Nursery and planting")
        loanRequired.add("wedding")
        loanRequired.add("Plant protection")
        loanRequired.add("Fertilizers")
        loanRequired.add("Wages")
        loanRequired.add("Stacking Export and Other expenses")
        customSpinnerLoanRequired=CustomSpinnerLoanRequired(this,R.layout.item_credit,loanRequired!!)
        activityCreditLoanRequiredBinding!!.spinnerLoanRequired.adapter=customSpinnerLoanRequired
        activityCreditLoanRequiredBinding!!.spinnerLoanRequired.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                loanrequiredStr=loanRequired.get(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }
}