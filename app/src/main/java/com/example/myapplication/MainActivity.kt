package com.example.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var presentValue: Button
    private lateinit var increment: Button
    private lateinit var decrement: Button
    private lateinit var textview:TextView
    var myService:MyService?=null
    var bind=false

    private var serviceConnection=object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder=p1 as MyService.Mybinder
            myService=binder.getService()
            bind=true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            bind=false
            myService=null
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textview=findViewById(R.id.text_view)
        presentValue=findViewById(R.id.present_value)
        increment=findViewById(R.id.increment)
        decrement=findViewById(R.id.decrement)
        increment.setOnClickListener {
            myService?.increment()
        }
        decrement.setOnClickListener {
            myService?.decrement()
        }
        presentValue.setOnClickListener {
            textview.text = (myService?.getCounterValue()!!).toString()
        }
    }

    override fun onStart() {
        super.onStart()
        val intent= Intent(this, MyService::class.java)
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (bind){
            unbindService(serviceConnection)
        }
    }
}