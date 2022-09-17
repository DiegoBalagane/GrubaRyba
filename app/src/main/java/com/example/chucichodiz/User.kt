package com.example.chucichodiz

import com.google.firebase.database.IgnoreExtraProperties



data class User(val userID:String?=null, val emial: String?=null, val name: String?=null, val lastname: String?=null, var password: String?=null, val photo: String?=null, val counterFish: Int?=null) {

}
