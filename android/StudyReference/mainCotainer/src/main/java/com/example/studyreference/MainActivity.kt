package com.example.studyreference

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.codelabstudylib.workManager.WorkManagerActivity
import com.example.studyreference.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        binding.testBtn.setOnClickListener {
            val intent = Intent(
                this, WorkManagerActivity::class.java
            ).apply {
                action = Intent.ACTION_MAIN
            }
            startActivity(intent)
        }
    }
}