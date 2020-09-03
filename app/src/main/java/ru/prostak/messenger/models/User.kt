package ru.prostak.messenger.models

data class User(
    val id: String = "",
    var username:String = "",
    var bio: String = "",
    var fullname: String = "",
    val status: String = "",
    var phone: String = "",
    var photoUrl: String = ""
)