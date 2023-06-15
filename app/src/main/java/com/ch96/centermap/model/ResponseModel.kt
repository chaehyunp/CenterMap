package com.ch96.centermap.model

data class ResponseModel(var data:MutableList<CenterData>)

data class CenterData( var id:Int,
                       var centerName:String,
                       var sido:String,
                       var sigungu:String,
                       var facilityName:String,
                       var zipCode:String,
                       var address:String,
                       var lat:String,
                       var lng:String,
                       var createdAt:String,
                       var centerType:String,
                       var org:String,
                       var phoneNumber:String
)