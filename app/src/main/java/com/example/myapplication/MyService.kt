package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast

class MyService : Service() {
    private var counter=0
    inner class Mybinder: Binder() {
        fun getService():MyService=this@MyService
    }
    override fun onBind(intent: Intent): IBinder {
        return Mybinder()
    }
    fun getCounterValue(): Int {
        return counter
    }
    fun presentValue(){
        Toast.makeText(this, "$counter", Toast.LENGTH_SHORT).show()
    }
    fun increment(){
        counter++
    }
    fun decrement(){
        counter--
    }
}