package com.recam.screens.videoplayscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.Toast
import com.potyvideo.library.AndExoPlayerView
import com.recam.databinding.ActivityVideoPlayBinding
import com.recam.model.LoginResponse
import com.recam.model.VideoLikeResponse
import com.sculptee.utils.customprogress.CustomProgressDialog
import com.wecompli.network.ApiInterface
import com.wecompli.network.Retrofit
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoPlayActivity : AppCompatActivity() {
    var activityVideoPlayBinding:ActivityVideoPlayBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityVideoPlayBinding= ActivityVideoPlayBinding.inflate(LayoutInflater.from(this))
        setContentView(activityVideoPlayBinding!!.root)
        val videoId=intent.getStringExtra(PreferenceConstent.videoId)
        var title=intent.getStringExtra(PreferenceConstent.videoTitle)
        var videourl=intent.getStringExtra(PreferenceConstent.videourl)
        var videodesc=intent.getStringExtra(PreferenceConstent.videoTitle)
        var categoryname=intent.getStringExtra(PreferenceConstent.categoryname)
        var likecount=intent.getStringExtra(PreferenceConstent.like)
        var viewcount=intent.getStringExtra(PreferenceConstent.view)
        activityVideoPlayBinding!!.catName.setText(categoryname)
        activityVideoPlayBinding!!.videoTitle.setText(title)
        activityVideoPlayBinding!!.videoDesc.setText(videodesc)
      //  activityVideoPlayBinding!!.likeCount.setText(likecount)
       // activityVideoPlayBinding!!.viewcount.setText(viewcount)
      //  val aUrl: String = videourl!!.replace("http", "https")
        activityVideoPlayBinding!!.andExoPlayerView.setSource(videourl!!);
        activityVideoPlayBinding!!.imgBack.setOnClickListener {
            finish()
        }

        activityVideoPlayBinding!!.btnCall.setOnClickListener {
            Toast.makeText(this,"विकास जारी है",Toast.LENGTH_LONG).show()
        }

        activityVideoPlayBinding!!.rlLike.setOnClickListener {
            callLikeApiclivk(videoId)
        }
        Handler().postDelayed({
            callvideoviewApi(videoId)
        },2000)
        callLikeApi(videoId)
    }


    private fun callLikeApi(videoId: String?) {
        var loginUserData: LoginResponse.LoginUserData?= AppSheardPreference(this).getUser(PreferenceConstent.userData)
        val map: HashMap<String, String> = HashMap()
        map.put("video_id", videoId!!)

       // val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
       // customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        val callApi= apiInterface.callVideoLIkeApi("Bearer " + loginUserData!!.token, map)
        callApi.enqueue(object : Callback<VideoLikeResponse> {
            override fun onResponse(call: Call<VideoLikeResponse>, response: Response<VideoLikeResponse>) {
                //customProgress.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        activityVideoPlayBinding!!.likeCount.setText(response!!.body()!!.data.get(0).likes.toString())
                        activityVideoPlayBinding!!.viewcount.setText(response!!.body()!!.data.get(0).views.toString())

                    } else
                        Toast.makeText(this@VideoPlayActivity, response!!.body()!!.message, Toast.LENGTH_LONG).show()

                } else
                    Toast.makeText(this@VideoPlayActivity, "Something wrong. Try later", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<VideoLikeResponse>, t: Throwable) {
               // customProgress.hideProgress()
            }
        })

    }

    private fun callLikeApiclivk(videoId: String?) {
        var loginUserData: LoginResponse.LoginUserData?= AppSheardPreference(this).getUser(PreferenceConstent.userData)
        val map: HashMap<String, String> = HashMap()
        map.put("video_id", videoId!!)
        //  map["name"] = activityRegistrationBinding!!.etName.text.toString()
        // map["phone"] = activityRegistrationBinding!!.etPnumber.text.toString()
         val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        val callApi= apiInterface.callVideoLIkeApi("Bearer " + loginUserData!!.token, map)
        callApi.enqueue(object : Callback<VideoLikeResponse> {
            override fun onResponse(call: Call<VideoLikeResponse>, response: Response<VideoLikeResponse>) {
                customProgress.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                        activityVideoPlayBinding!!.likeCount.setText(response!!.body()!!.data.get(0).likes.toString())
                        activityVideoPlayBinding!!.viewcount.setText(response!!.body()!!.data.get(0).views.toString())

                    } else
                        Toast.makeText(this@VideoPlayActivity, response!!.body()!!.message, Toast.LENGTH_LONG).show()

                } else
                    Toast.makeText(this@VideoPlayActivity, "Something wrong. Try later", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<VideoLikeResponse>, t: Throwable) {
                 customProgress.hideProgress()
            }
        })

    }
    private fun callvideoviewApi(videoId: String?) {
        var loginUserData: LoginResponse.LoginUserData?= AppSheardPreference(this).getUser(PreferenceConstent.userData)
        val map: HashMap<String, String> = HashMap()
        map.put("video_id", videoId!!)
        //  map["name"] = activityRegistrationBinding!!.etName.text.toString()
        // map["phone"] = activityRegistrationBinding!!.etPnumber.text.toString()
        // val  customProgress: CustomProgressDialog = CustomProgressDialog().getInstance()
        // customProgress.showProgress(this, "Please Wait..", false)
        val apiInterface= Retrofit.retrofitInstance?.create(ApiInterface::class.java)
        val callApi= apiInterface.callVideoViewApi("Bearer " + loginUserData!!.token, map)
        callApi.enqueue(object : Callback<VideoLikeResponse> {
            override fun onResponse(call: Call<VideoLikeResponse>, response: Response<VideoLikeResponse>) {
                //customProgress.hideProgress()
                if (response.isSuccessful) {
                    if (response.body()!!.status == 200) {
                      //  activityVideoPlayBinding!!.likeCount.setText(response!!.body()!!.data.get(0).likes.toString())
                       // activityVideoPlayBinding!!.viewcount.setText(response!!.body()!!.data.get(0).views.toString())

                    } else
                        Toast.makeText(this@VideoPlayActivity, response!!.body()!!.message, Toast.LENGTH_LONG).show()

                } else
                    Toast.makeText(this@VideoPlayActivity, "Something wrong. Try later", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<VideoLikeResponse>, t: Throwable) {
                // customProgress.hideProgress()
            }
        })

    }
}