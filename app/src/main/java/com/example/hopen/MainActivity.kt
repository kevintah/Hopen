package com.example.hopen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.hopen.data.Datasource


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView: TextView = findViewById(R.id.textview)
        textView.text = Datasource().loadAffirmations().size.toString()



        val btnStartAnotherActivity: Button = findViewById(R.id.btnStartAnotherActivity)
        btnStartAnotherActivity.setOnClickListener {
            val intent = Intent(this, AnotherActivity::class.java)
            // start your next activity
            startActivity(intent)
        }


    }


}