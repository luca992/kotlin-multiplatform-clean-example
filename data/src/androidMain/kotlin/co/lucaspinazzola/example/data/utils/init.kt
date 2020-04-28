package co.lucaspinazzola.example.data.utils

import android.app.Application

lateinit var application: Application

fun initData(application: Application){
    co.lucaspinazzola.example.data.utils.application = application
}