package com.recam.screens.categoryvideolist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recam.adapter.CategoryVideoListAdapter
import com.recam.databinding.ActivityCategoryVideoListBinding
import com.recam.model.CategoryApiResponse
import com.recam.model.CategoryVideoListResponse
import com.recam.model.LoginResponse
import com.recam.screens.videoplayscreen.VideoPlayActivity
import com.recam.utils.OnitemClickInterface
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoryVideoList: AppCompatActivity(){
    var  activityCategoryVideoListBinding:ActivityCategoryVideoListBinding?=null
    var categoryVideoListAdapter:CategoryVideoListAdapter?=null
    var categoryvideolist:ArrayList<CategoryVideoListResponse.Videodata>?= ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCategoryVideoListBinding= ActivityCategoryVideoListBinding.inflate(LayoutInflater.from(this))

        setContentView(activityCategoryVideoListBinding!!.root)
        val layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        activityCategoryVideoListBinding!!.recCategoryVidepo.setLayoutManager(layoutManager)
        val categoryid = intent!!.getStringExtra(PreferenceConstent.categoryId)
        val categoryname=intent!!.getStringExtra(PreferenceConstent.categoryname)
        activityCategoryVideoListBinding!!.categoryname.setText(categoryname)
        callApiCategoris(categoryid)
        activityCategoryVideoListBinding!!.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun callApiCategoris(category_id: String?) {
        var loginUserData: LoginResponse.LoginUserData?= AppSheardPreference(this).getUser(PreferenceConstent.userData)
        val map: HashMap<String, String> = HashMap()
        map.put("language",PreferenceConstent.language)
        map.put("category_id",category_id!!)
        //  map["name"] = activityRegistrationBinding!!.etName.text.toString()
        // map["phone"] = activityRegistrationBinding!!.etPnumber.text.toString()
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        val callApi= apiInterface.callCategoryVideoApi("Bearer "+loginUserData!!.token,map )
        callApi.enqueue(object : Callback<CategoryVideoListResponse> {
            override fun onResponse(call: Call<CategoryVideoListResponse>, response: Response<CategoryVideoListResponse>) {
                customProgress.hideProgress()
                if (response.isSuccessful){
                    if(response.body()!!.status==200){
                        // Toast.makeText(this@HomeActivity,response!!.body()!!.message, Toast.LENGTH_LONG).show()
                        categoryvideolist=response!!.body()!!.data
                        categoryVideoListAdapter= CategoryVideoListAdapter(this@CategoryVideoList, categoryvideolist!!,
                            object : OnitemClickInterface { override fun ItemClick(position: Int) {
                                super.ItemClick(position)
                                var categoryVideoAetivity = Intent(this@CategoryVideoList, VideoPlayActivity::class.java)
                                categoryVideoAetivity.putExtra(PreferenceConstent.videoTitle, categoryvideolist!!.get(position).video_title)
                                categoryVideoAetivity.putExtra(PreferenceConstent.videourl, categoryvideolist!!.get(position).video_url)
                                categoryVideoAetivity.putExtra(PreferenceConstent.categoryname, categoryvideolist!!.get(position).category)
                                categoryVideoAetivity.putExtra(PreferenceConstent.videodese, categoryvideolist!!.get(position).video_description)
                                categoryVideoAetivity.putExtra(PreferenceConstent.like, categoryvideolist!!.get(position).likes.toString())
                                startActivity(categoryVideoAetivity)

                            }
                            })

                        // at last set adapter to recycler view.
                        activityCategoryVideoListBinding!!.recCategoryVidepo.setAdapter(categoryVideoListAdapter)
                    }else
                        Toast.makeText(this@CategoryVideoList,response!!.body()!!.message, Toast.LENGTH_LONG).show()

                }else
                    Toast.makeText(this@CategoryVideoList,"Something wrong. Try later", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<CategoryVideoListResponse>, t: Throwable) {
                customProgress.hideProgress()
            }
        })

    }

}