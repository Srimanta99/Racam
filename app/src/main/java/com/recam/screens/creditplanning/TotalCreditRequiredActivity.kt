package com.recam.screens.creditplanning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.recam.R
import com.recam.databinding.ActivityTotalCreditRequiredBinding

class TotalCreditRequiredActivity : AppCompatActivity() {
    var activityTotalCreditRequiredBinding:ActivityTotalCreditRequiredBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTotalCreditRequiredBinding= ActivityTotalCreditRequiredBinding.inflate(LayoutInflater.from(this))
        setContentView(activityTotalCreditRequiredBinding!!.root)
        activityTotalCreditRequiredBinding!!.etTotal.setText(intent.getStringExtra("loanamount"))
        activityTotalCreditRequiredBinding!!.btnNext.setOnClickListener {
           if (! activityTotalCreditRequiredBinding!!.etTotal.text.toString().equals("")){
               if(! activityTotalCreditRequiredBinding!!.etyour.text.toString().equals("")){
                   val intent=Intent(this,CreditEmiCalculatorActivity::class.java)
                   val totalCredit=(activityTotalCreditRequiredBinding!!.etTotal.text.toString()).toInt()
                   val yourinvestment=(activityTotalCreditRequiredBinding!!.etyour.text.toString()).toInt()

                    intent.putExtra("yourinvestment",activityTotalCreditRequiredBinding!!.etyour.text.toString())
                     intent.putExtra("loanrequired",(totalCredit-yourinvestment).toString())
                   startActivity(intent)
               }
               else
                   Toast.makeText(this,"Total your investment",Toast.LENGTH_LONG).show()
           }else
               Toast.makeText(this,"Total credit required",Toast.LENGTH_LONG).show()

        }
        activityTotalCreditRequiredBinding!!.imgBack.setOnClickListener {
            finish()
        }
    }
}