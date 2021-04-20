package com.wecompli.utils.sheardpreference

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.recam.model.LoginResponse


class AppSheardPreference(activity: Activity) {
    internal var sharedpreferences: SharedPreferences
    internal var editor: SharedPreferences.Editor

    init {
        sharedpreferences = activity.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)
        editor = sharedpreferences.edit()
    }


    fun setvalue_in_preference(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
        editor.apply()
    }

    fun getvalue_in_preference(key: String): String {
        return sharedpreferences.getString(key, "")!!
    }

   fun setUserData(key: String,user:LoginResponse.LoginUserData){
       // val editor=sharedpreferences!!.edit()
        editor.putString(key, Gson().toJson(user))
        editor.apply()
    }

    fun getUser(key: String):LoginResponse.LoginUserData?{
        var sata = sharedpreferences!!.getString(key, "")
        return Gson().fromJson(sata,LoginResponse.LoginUserData::class.java)
    }

    /*  fun setSiteDetails(key: String,sitemodel : SiteListModel.RowList) {
         editor.putString(key, Gson().toJson(sitemodel))
         editor.apply()
     }

     fun getSite(key: String):SiteListModel.RowList?{
         var sata = sharedpreferences!!.getString(key, "")
         return Gson().fromJson(sata,SiteListModel.RowList::class.java)
     }

     fun setUserDetails(key: String,userModel : SiteUserListModel.UserList) {
         editor.putString(key, Gson().toJson(userModel))
         editor.apply()
     }

     fun getSelectedUser(key: String): SiteUserListModel.UserList?{
         var sata = sharedpreferences!!.getString(key, "")
         return Gson().fromJson(sata,SiteUserListModel.UserList::class.java)
     }
 */

    fun clerpreference() {
        editor.clear()
        editor.commit()
    }

    companion object {
        val MyPREFERENCES = "MyPrefs"
    }

}
