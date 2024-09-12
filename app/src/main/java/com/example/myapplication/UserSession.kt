package com.example.myapplication

object UserSession{
    var isLoggedIn: Boolean = false
    var user: User? = null

    fun clearSession(){
        isLoggedIn = false
        user = User("", 0, "", "", "")
    }
}
