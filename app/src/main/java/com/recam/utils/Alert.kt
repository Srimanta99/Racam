package com.recam.utils

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.recam.R
import com.recam.screens.home.HomeActivity


class Alert {
     companion object {
       /*  fun showalert(activity: Activity, message: String) {
             //  var deviceResolution:DeviceResolution?=null
             var deviceResolution = DeviceResolution(activity)
             val alertDialog = Dialog(activity, R.style.Transparent)
             *//*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
             alertDialog.setMessage(message)*//*
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View = LayoutInflater.from(activity).inflate(R.layout.alert_layout, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btn_ok: Button = view.findViewById(R.id.btn_ok)
             btn_ok.typeface = deviceResolution.getgothmbold(activity)
             tv_message.typeface = deviceResolution.getgothmlight(activity)
             btn_ok.setOnClickListener {
                 alertDialog.dismiss()
             }
             tv_message.setText(message)
             alertDialog.show()
             *//*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                 DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
             alertDialog.show()*//*
         }
*/

         fun showalertToGoHomePage(activity: Activity, message: String) {
             //  var deviceResolution:DeviceResolution?=null

             val alertDialog = Dialog(activity, R.style.Transparent)
             /*alertDialog.setTitle(activity.resources.getString(R.string.app_name))
             alertDialog.setMessage(message)*/
             alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
             val view: View = LayoutInflater.from(activity).inflate(R.layout.alert_layout, null)
             alertDialog.setContentView(view)
             alertDialog.setCancelable(false)
             val tv_message: TextView = view.findViewById(R.id.tv_message)
             val btn_ok: Button = view.findViewById(R.id.btn_ok)
             btn_ok.setOnClickListener {
                 alertDialog.dismiss()
                 val intte=Intent(activity, HomeActivity::class.java);
                 intte.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                 activity.startActivity(intte)
             }
             tv_message.setText(message)
             alertDialog.show()
             /*alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                 DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
             alertDialog.show()*/
         }

     }
}