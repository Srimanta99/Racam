package com.wecompli.network


import com.google.gson.JsonObject
import com.recam.model.*
import okhttp3.ResponseBody

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST(NetworkUtility.LOGIN)
    fun callLoginApi(@FieldMap  data:Map<String, String>):Call<LoginResponse>

    @FormUrlEncoded
    @POST(NetworkUtility.OTP)
    fun callgrtOtp(@FieldMap  data:Map<String, String>):Call<OtpResponse>


    @FormUrlEncoded
    @POST(NetworkUtility.REGISTRATION)
    fun callRegistrationApi(@FieldMap  data:Map<String, String>):Call<RegistrationResponseModel>

    @FormUrlEncoded
    @POST(NetworkUtility.FEEDBACK)
    fun callfeedbackApi(@Header("Authorization") header:String,@FieldMap  data:Map<String, String>):Call<FeedbackSubmitResponse>

    @FormUrlEncoded
    @POST(NetworkUtility.CATEGORY)
    fun callCategoryApi(@Header("Authorization") header:String,@FieldMap  data:Map<String, String>):Call<CategoryApiResponse>


    @FormUrlEncoded
    @POST(NetworkUtility.CATEGORYVIDEO)
    fun callCategoryVideoApi(@Header("Authorization") header:String,@FieldMap  data:Map<String, String>):Call<CategoryVideoListResponse>


    @FormUrlEncoded
    @POST(NetworkUtility.HOME)
    fun callHomeApi(@Header("Authorization") header:String,@FieldMap  data:Map<String, String>):Call<HomeApiResponse>


    /*@POST(NetworkUtility.LOGIN)
    fun callLogInApi(@Body body: JsonObject): Call<LoginResponseModel>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.SITELIST)
    fun callSiteListApi(@Header("Authorization") token:String,@Body body: JsonObject): Call<SiteListModel>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.CREATE_USER)
    fun caallCreateUserApi(@Header("Authorization") token:String, @Body body: JsonObject): Call<AddUserResponse>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.SITE_USER_UPDATE)
    fun callSiteUserUpdateApi(@Header("Authorization") token:String, @Body body: JsonObject): Call<AddUserResponse>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.SITE_USER_LIST)
    fun callSiteUserListApi(@Header("Authorization") token:String, @Body body: JsonObject): Call<SiteUserListModel>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.FAULTLIST)
    fun callFaultApi(@Header("Authorization") token:String, @Body body: JsonObject): Call<FaultListModel>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.FAULTREPAIR)
    fun callApiforFaultRepair(@Header("Authorization") token:String, @Body body: JsonObject): Call<AddUserResponse>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.DOCUMENT_LIST)
    fun calldocumetList(@Header("Authorization") token:String,@Body body: JsonObject): Call<DocumentListModel>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.FAULTCOUNT)
    fun callFaultCountApt(@Header("Authorization") token:String,@Body body: JsonObject): Call<FaultCountModel>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.DOCUMENT_REMOVE)
    fun callApifordocumentremove(@Header("Authorization") token:String, @Body body: JsonObject): Call<AddUserResponse>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.PASSWORD_UPDATE)
    fun callApifordochangepassword(@Header("Authorization") token:String, @Body body: JsonObject): Call<AddUserResponse>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.PROFILE_UPDATE)
    fun callApiforupdateprofile(@Header("Authorization") token:String, @Body body: JsonObject): Call<AddUserResponse>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.COUNTRYLIST)
    fun callApiforcountrylist(@Header("Authorization") token:String, @Body body: JsonObject): Call<CountryListModel>


    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.EMAILLIST)
    fun callApiforemaillist(@Header("Authorization") token:String, @Body body: JsonObject): Call<EmailListModel>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.EMAILCREATE)
    fun callApiforemailadd(@Header("Authorization") token:String, @Body body: JsonObject): Call<AddUserResponse>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.SITE_REMOVE)
    fun callApifordeletesite(@Header("Authorization") token:String, @Body body: JsonObject): Call<AddUserResponse>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.User_Remove)
    fun callApifordeleteUser(@Header("Authorization") token:String, @Body body: JsonObject): Call<AddUserResponse>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.SUBCRIPTION)
    fun callApiforSubPackage(@Header("Authorization") token:String, @Body body: JsonObject): Call<SubCriptionPackagResponseemodel>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.PAYMENT)
    fun callApiforpayment(@Header("Authorization") token:String, @Body body: JsonObject): Call<AddUserResponse>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.SUBCRIPTION_DETAILS)
    fun callApiforsubcription(@Header("Authorization") token:String, @Body body: JsonObject): Call<SubcriptionDeatilsResponse>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.FREEPACKAGE)
    fun callApiforfreesubcription(@Header("Authorization") token:String, @Body body: JsonObject): Call<AddUserResponse>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.CHECKUSERSUBCRIPTION)
    fun callApiforCheckUserubcription(@Header("Authorization") token:String, @Body body: JsonObject): Call<CheckUserSubcriptionResponse>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.SiteUserListForRemove)
    fun callApiforsiteandUser(@Header("Authorization") token:String, @Body body: JsonObject): Call<SiteAndUserModel>

    @Headers("Content-Type: application/json")
    @POST(NetworkUtility.REMOVEUSERSITE)
    fun callApiforRemovesiteandUser(@Header("Authorization") token:String, @Body body: JsonObject): Call<SiteAndUserModel>

*/

}