package com.ch96.centermap.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemModel constructor(val context: Context) {


    // API 주소
    //https://api.odcloud.kr/api/15077586/v1/centers?page=10&perPage=10&serviceKey=bNmSjmL3NWL%2FmAmsQV0SyDT%2B8DCdZckhVg5%2FtSsmJHa47eBZBE%2BaFvCHYxeM1Dsz2FcgQ64elqYL3mr6GUyjOg%3D%3D
    var baseUrl = "https://api.odcloud.kr/api/15077586/v1/"
    var serviceKey = "bNmSjmL3NWL/mAmsQV0SyDT+8DCdZckhVg5/tSsmJHa47eBZBE+aFvCHYxeM1Dsz2FcgQ64elqYL3mr6GUyjOg=="

}