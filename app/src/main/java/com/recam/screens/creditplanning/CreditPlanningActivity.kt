package com.recam.screens.creditplanning

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.recam.R
import com.recam.adapter.CustomSpinnerAdapter
import com.recam.adapter.CustomlandTypeAdapter
import com.recam.databinding.ActivityCreditPlanningBinding
import com.recam.model.DistrictApiresponseModel
import com.recam.model.LoanRegisterApiResponse
import com.recam.model.LoginResponse
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class CreditPlanningActivity : AppCompatActivity() {
    var activityCreditPlanningBinding:ActivityCreditPlanningBinding?=null
    var customSpinnerAdapter:CustomSpinnerAdapter?=null
    var customSpinnerlandtypey: CustomlandTypeAdapter?=null
    val categories: MutableList<String> = ArrayList()
    var dislist:ArrayList<DistrictApiresponseModel.DistList>?=null
    var landtypelist:ArrayList<String>?= ArrayList()
    var selecteddistname:String?=""
    var landtypename:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCreditPlanningBinding=ActivityCreditPlanningBinding.inflate(LayoutInflater.from(this))
        setContentView(activityCreditPlanningBinding!!.root)
        activityCreditPlanningBinding!!.imgBack.setOnClickListener {
            finish()
        }
       callApifordistrict()

        categories.add("जिला भरें")
        //categories.add("Item 1")
       // categories.add("Item 2")
        //

        landtypelist!!.add("भूमि का प्रकार")
        landtypelist!!.add("पट्टे की भूमि")
        landtypelist!!.add("खुद की जमीन")
        customSpinnerlandtypey = CustomlandTypeAdapter(this@CreditPlanningActivity, R.layout.item_credit, landtypelist!!)
        activityCreditPlanningBinding!!.spinnerLandtype.adapter=customSpinnerlandtypey
        activityCreditPlanningBinding!!.spinnerLandtype.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2==0)
                    landtypename=""
                else
                landtypename=landtypelist!!.get(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        customSpinnerAdapter= CustomSpinnerAdapter(this, R.layout.item_credit, categories)
        activityCreditPlanningBinding!!.spinner.adapter=customSpinnerAdapter;
        activityCreditPlanningBinding!!.spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selecteddistname=categories.get(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        activityCreditPlanningBinding!!.btnNext.setOnClickListener {
           // startActivity(Intent(this, CreditCalculatorForActivity::class.java))
          //  loanApplicationApi()
           checkvalidation();
        }

        activityCreditPlanningBinding!!.tvAge.setOnClickListener {
            val newFragment: DialogFragment = DatePickerFragment( activityCreditPlanningBinding!!.tvAge)
            newFragment!!.show(supportFragmentManager!!,"datepicker")
        }
    }

    private fun checkvalidation() {
        if (landtypename.equals("")){
            Toast.makeText(this,"Enter Field type  ",Toast.LENGTH_LONG).show()
            return
        }
        else if (activityCreditPlanningBinding!!.etPno.text.toString().equals("")) {
            Toast.makeText(this,"Enter phone number",Toast.LENGTH_LONG).show()
            return
        }
        else if (activityCreditPlanningBinding!!.etFname.text.toString().equals("")){
            Toast.makeText(this,"Enter first name ",Toast.LENGTH_LONG).show()
            return
        }

        else if (activityCreditPlanningBinding!!.etLastname.text.toString().equals("")) {
            Toast.makeText(this,"Enter last name ",Toast.LENGTH_LONG).show()
            return
        }

        else
         loanApplicationApi()
    }

    class DatePickerFragment(val tvAge: TextView) : DialogFragment(), OnDateSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c: Calendar = Calendar.getInstance()
            val year: Int = c.get(Calendar.YEAR)
            val month: Int = c.get(Calendar.MONTH)
            val day: Int = c.get(Calendar.DAY_OF_MONTH)
            val dialog = DatePickerDialog(activity!!, this, year, month, day)
            dialog.datePicker.maxDate = c.getTimeInMillis()
            return dialog
        }

        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
            tvAge!!.setText(day.toString()+"/"+month.toString()+"/"+year.toString())
        }
    }
    private fun loanApplicationApi() {
        var loginUserData: LoginResponse.LoginUserData?= AppSheardPreference(this).getUser(
            PreferenceConstent.userData
        )
        val map: HashMap<String, String> = HashMap()
        map.put("first_name", activityCreditPlanningBinding!!.etFname.text.toString())
        map.put("last_name", activityCreditPlanningBinding!!.etLastname.text.toString())
        map.put("phone_no", activityCreditPlanningBinding!!.etPno.text.toString())
        map.put("district", selecteddistname!!)
      //  map.put("gender",activityCreditPlanningBinding.)
        map.put("age", activityCreditPlanningBinding!!.tvAge.text.toString())
        map.put("village", activityCreditPlanningBinding!!.etVillage.text.toString())
        map.put("panchayat", activityCreditPlanningBinding!!.etPanchyat.text.toString())
        map.put("SHG_name", activityCreditPlanningBinding!!.etPgsg.text.toString())
        map.put("block", activityCreditPlanningBinding!!.etBlock.text.toString())
        map.put("land_type",landtypename!!)
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        val callApi= apiInterface.callloanApplicationApi("Bearer " + loginUserData!!.token, map)
        callApi.enqueue(object : Callback<LoanRegisterApiResponse> {
            override fun onResponse(
                call: Call<LoanRegisterApiResponse>,
                response: Response<LoanRegisterApiResponse>) {
                customProgress.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        AppSheardPreference(this@CreditPlanningActivity).setvalue_in_preference(PreferenceConstent.creditUserId,response!!.body()!!.id.toString())
                        startActivity(Intent(this@CreditPlanningActivity, CreditCalculatorForActivity::class.java))

                    }

                } else
                    Toast.makeText(this@CreditPlanningActivity, "Something wrong. Try later", Toast.LENGTH_LONG).show()

            }

            override fun onFailure(call: Call<LoanRegisterApiResponse>, t: Throwable) {
                customProgress.hideProgress()
            }
        })
    }

    private fun callApifordistrict() {
       var loginUserData: LoginResponse.LoginUserData?= AppSheardPreference(this).getUser(
           PreferenceConstent.userData
       )
        val map: HashMap<String, String> = HashMap()
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        val callApi= apiInterface.calldistrictApi("Bearer " + loginUserData!!.token)
        callApi.enqueue(object : Callback<DistrictApiresponseModel> {
            override fun onResponse(
                call: Call<DistrictApiresponseModel>,
                response: Response<DistrictApiresponseModel>
            ) {
                customProgress.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        dislist = response!!.body()!!.data
                        if (dislist!!.size > 0) {
                            for (i in 0 until dislist!!.size) {
                                categories.add(dislist!!.get(i).district_name)
                            }
                            val adpter = ArrayAdapter<String>(
                                this@CreditPlanningActivity,
                                R.layout.item_credit,
                                categories
                            )
                            adpter.setDropDownViewResource(R.layout.item_credit);
                        }
                    }
                    //else
                    //Toast.makeText(this@CreditCalculatorForActivity, response!!.body()!!.message, Toast.LENGTH_LONG).show()

                } else
                    Toast.makeText(
                        this@CreditPlanningActivity,
                        "Something wrong. Try later",
                        Toast.LENGTH_LONG
                    ).show()
            }

            override fun onFailure(call: Call<DistrictApiresponseModel>, t: Throwable) {
                customProgress.hideProgress()
            }
        })
    }
}