package com.recam.screens.home

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recam.R
import com.recam.adapter.CategoryAdapter
import com.recam.adapter.HomeCategoryVideoAdapter
import com.recam.adapter.TopCategoryAdapter
import com.recam.databinding.ActivityMainBinding
import com.recam.databinding.ContentMainBinding
import com.recam.model.CategoryApiResponse
import com.recam.model.HomeApiResponse
import com.recam.model.LoginResponse
import com.recam.screens.aboutus.AboutUsActivity
import com.recam.screens.categoryvideolist.CategoryVideoList
import com.recam.screens.creditplanning.CreditPlanningActivity
import com.recam.screens.feedback.FeedbackActivity
import com.recam.screens.login.LoginActivity
import com.recam.screens.profile.ProfileActivity
import com.recam.screens.termcondition.TermConditionActivity
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


class HomeActivity : AppCompatActivity() {
    var activityMainBinding:ActivityMainBinding?=null
    var contentMainBinding:ContentMainBinding?=null
    var topCategoryAdapter:TopCategoryAdapter?=null
    var loginUserData:LoginResponse.LoginUserData?=null
    var categorylist:ArrayList<CategoryApiResponse.CategoryData>?= ArrayList()
    var categoryvideolist:ArrayList<HomeApiResponse.CategoryData>?= ArrayList()
    var categoryAdapter:CategoryAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       activityMainBinding= ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(activityMainBinding!!.root)
        contentMainBinding=activityMainBinding!!.contentMain
        loginUserData=AppSheardPreference(this).getUser(PreferenceConstent.userData)
        activityMainBinding!!.tvName.setText(loginUserData!!.name)
        activityMainBinding!!.tvNo.setText(loginUserData!!.phone)
        Glide.with(this)
                .load(loginUserData!!.profile_photo_url)
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .into(activityMainBinding!!.profileImage);
        contentMainBinding!!.imgMenu.setOnClickListener {
            activityMainBinding!!.drawerLayout.openDrawer(Gravity.LEFT)
        }
        activityMainBinding!!.llLogout.setOnClickListener {
            AppSheardPreference(this).clerpreference()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        activityMainBinding!!.rlHeader.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        activityMainBinding!!.llFeedback.setOnClickListener {
            startActivity(Intent(this, FeedbackActivity::class.java))
        }
        activityMainBinding!!.llAboutus.setOnClickListener {
            startActivity(Intent(this, AboutUsActivity::class.java))
        }
        activityMainBinding!!.llTermcondition.setOnClickListener {
            startActivity(Intent(this, TermConditionActivity::class.java))
        }
        activityMainBinding!!.llCreditPlanningt.setOnClickListener {
            startActivity(Intent(this, CreditPlanningActivity::class.java))
        }


       /* activityMainBinding!!.contentMain.recBottomcategory!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        activityMainBinding!!.contentMain.recBottomcategory!!.adapter=topCategoryAdapter*/
        callHomeApi()
        callApiCategoris()
    }
    private fun callApiCategoris() {
        var loginUserData: LoginResponse.LoginUserData?=AppSheardPreference(this).getUser(PreferenceConstent.userData)
        val map: HashMap<String, String> = HashMap()
        map.put("language", "hindi")
        //  map["name"] = activityRegistrationBinding!!.etName.text.toString()
        // map["phone"] = activityRegistrationBinding!!.etPnumber.text.toString()
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        val callApi= apiInterface.callCategoryApi("Bearer " + loginUserData!!.token, map)
        callApi.enqueue(object : Callback<CategoryApiResponse> {
            override fun onResponse(call: Call<CategoryApiResponse>, response: Response<CategoryApiResponse>) {
                customProgress.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        // Toast.makeText(this@HomeActivity,response!!.body()!!.message, Toast.LENGTH_LONG).show()
                        categorylist = response!!.body()!!.data
                        categoryAdapter = CategoryAdapter(this@HomeActivity, categorylist!!,
                            object : OnitemClickInterface {
                                override fun ItemClick(position: Int) {
                                    super.ItemClick(position)
                                    var categoryVideoAetivity = Intent(this@HomeActivity, CategoryVideoList::class.java)
                                    categoryVideoAetivity.putExtra(PreferenceConstent.categoryId, categorylist!!.get(position).id.toString())
                                    categoryVideoAetivity.putExtra(PreferenceConstent.categoryname, categorylist!!.get(position).name)
                                    startActivity(categoryVideoAetivity)
                                }
                            })
                        activityMainBinding!!.recCategory.setLayoutManager(LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false));
                        activityMainBinding!!.recCategory.adapter = categoryAdapter
                    } else
                        Toast.makeText(this@HomeActivity, response!!.body()!!.message, Toast.LENGTH_LONG).show()

                } else
                    Toast.makeText(this@HomeActivity, "Something wrong. Try later", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<CategoryApiResponse>, t: Throwable) {
                customProgress.hideProgress()
            }
        })

    }


    private fun callHomeApi() {
        var loginUserData: LoginResponse.LoginUserData?=AppSheardPreference(this).getUser(
            PreferenceConstent.userData
        )
        val map: HashMap<String, String> = HashMap()
        map.put("language",PreferenceConstent.language)
        //  map["name"] = activityRegistrationBinding!!.etName.text.toString()
        // map["phone"] = activityRegistrationBinding!!.etPnumber.text.toString()
        val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        val callApi= apiInterface.callHomeApi("Bearer " + loginUserData!!.token, map)
        callApi.enqueue(object : Callback<HomeApiResponse> {
            override fun onResponse(call: Call<HomeApiResponse>, response: Response<HomeApiResponse>) {
                customProgress.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        // Toast.makeText(this@HomeActivity,response!!.body()!!.message, Toast.LENGTH_LONG).show()
                        topCategoryAdapter = TopCategoryAdapter(this@HomeActivity, response!!.body()!!.data.latest_update,object :OnitemClickInterface{
                            override fun ItemClick(position: Int) {
                                super.ItemClick(position)

                                var categoryVideoAetivity = Intent(this@HomeActivity, VideoPlayActivity::class.java)
                                categoryVideoAetivity.putExtra(PreferenceConstent.categoryname, response!!.body()!!.data.latest_update.get(position).category)
                                categoryVideoAetivity.putExtra(PreferenceConstent.videodese, response!!.body()!!.data.latest_update.get(position).video_description)
                                categoryVideoAetivity.putExtra(PreferenceConstent.videoTitle, response!!.body()!!.data.latest_update.get(position).video_title)
                                categoryVideoAetivity.putExtra(PreferenceConstent.videourl, response!!.body()!!.data.latest_update.get(position).video_url)
                                categoryVideoAetivity.putExtra(PreferenceConstent.like,response!!.body()!!.data.latest_update.get(position).likes.toString())
                                startActivity(categoryVideoAetivity)
                            }
                        })
                        activityMainBinding!!.contentMain.recTopcategory.setLayoutManager(LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false));
                        activityMainBinding!!.contentMain.recTopcategory.adapter = topCategoryAdapter

                        categoryvideolist = response!!.body()!!.data.category
                        for (i in 0 until categoryvideolist!!.size) {
                            if (categoryvideolist!!.get(i).videos.size > 0)
                                setcategoryVideoList(categoryvideolist!!.get(i))
                        }
                    } else
                        Toast.makeText(this@HomeActivity, response!!.body()!!.message, Toast.LENGTH_LONG).show()

                } else
                    Toast.makeText(this@HomeActivity, "Something wrong. Try later", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<HomeApiResponse>, t: Throwable) {
                customProgress.hideProgress()
            }
        })

    }

    private fun setcategoryVideoList(catgoryresponse: HomeApiResponse.CategoryData) {
        var homeCategoryVideoAdapter : HomeCategoryVideoAdapter?=null
        val child: View = layoutInflater.inflate(R.layout.item_category_with_list, null)
        var rec_category_video:RecyclerView=child.findViewById(R.id.rec_category_video)
        var tv_cat_name:TextView=child.findViewById(R.id.tv_cat_name)
        tv_cat_name.setText(catgoryresponse.name)
        activityMainBinding!!.contentMain.llCategory.addView(child)
        homeCategoryVideoAdapter=HomeCategoryVideoAdapter(this,catgoryresponse.videos,object :OnitemClickInterface{
            override fun ItemClick(position: Int) {
                super.ItemClick(position)

                var categoryVideoAetivity = Intent(this@HomeActivity, VideoPlayActivity::class.java)
                categoryVideoAetivity.putExtra(PreferenceConstent.categoryname, catgoryresponse.videos.get(position).category)
                categoryVideoAetivity.putExtra(PreferenceConstent.videodese, catgoryresponse.videos.get(position).video_description)

                categoryVideoAetivity.putExtra(PreferenceConstent.videoTitle, catgoryresponse.videos.get(position).video_title)
                categoryVideoAetivity.putExtra(PreferenceConstent.videourl, catgoryresponse.videos.get(position).video_url)
                categoryVideoAetivity.putExtra(PreferenceConstent.like, catgoryresponse.videos.get(position).likes.toString())
                startActivity(categoryVideoAetivity)
            }
        })
        rec_category_video.setLayoutManager(LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false));
        rec_category_video.adapter=homeCategoryVideoAdapter;

    }
}