package com.recam.screens.termcondition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.recam.R
import com.recam.databinding.ActivityTermConditionBinding

class TermConditionActivity : AppCompatActivity() {
    var activityTermConditionBinding:ActivityTermConditionBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_term_condition)
        activityTermConditionBinding= ActivityTermConditionBinding.inflate(LayoutInflater.from(this))
        activityTermConditionBinding!!.imgBack.setOnClickListener {

        }
    }
}