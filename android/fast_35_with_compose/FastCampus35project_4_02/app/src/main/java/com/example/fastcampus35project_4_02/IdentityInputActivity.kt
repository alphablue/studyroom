package com.example.fastcampus35project_4_02

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.fastcampus35project_4_02.databinding.ActivityIndentityInputBinding

class IdentityInputActivity: AppCompatActivity() {
    private lateinit var binding: ActivityIndentityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_indentity_input)
        binding.view = this
    }
}