package com.recam.screens.videoplayscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.recam.R
import com.recam.databinding.ActivityCategoryVideoListBinding
import com.recam.databinding.ActivityVideoPlayBinding
import com.wecompli.utils.sheardpreference.PreferenceConstent

class VideoPlayActivity : AppCompatActivity() {
    var activityVideoPlayBinding:ActivityVideoPlayBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityVideoPlayBinding= ActivityVideoPlayBinding.inflate(LayoutInflater.from(this))
       setContentView(activityVideoPlayBinding!!.root)
        var title=intent.getStringExtra(PreferenceConstent.videoTitle)
        var videourl=intent.getStringExtra(PreferenceConstent.videourl)
        var videodesc=intent.getStringExtra(PreferenceConstent.videoTitle)
        var categoryname=intent.getStringExtra(PreferenceConstent.categoryname)
        var likecount=intent.getStringExtra(PreferenceConstent.like)
        activityVideoPlayBinding!!.catName.setText(categoryname)
        activityVideoPlayBinding!!.videoTitle.setText(title)
        activityVideoPlayBinding!!.videoDesc.setText(videodesc)
        activityVideoPlayBinding!!.likeCount.setText(likecount)
        val aUrl: String = videourl!!.replace("http", "https")
        activityVideoPlayBinding!!.andExoPlayerView.setSource(aUrl);
        activityVideoPlayBinding!!.imgBack.setOnClickListener {
            finish()
        }
        activityVideoPlayBinding!!.btnCall.setOnClickListener {
            Toast.makeText(this,"विकास जारी है",Toast.LENGTH_LONG).show()
        }
    }
}