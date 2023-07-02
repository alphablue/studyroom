package com.example.fastcampus35project_4_02

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.fastcampus35project_4_02.databinding.ActivityPinBinding
import com.example.fastcampus35project_4_02.widget.ShuffleNumberKeyboard

class PinActivity: AppCompatActivity(), ShuffleNumberKeyboard.KeyPadListener {

    private lateinit var binding: ActivityPinBinding
    private val viewModel: PinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pin)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.shuffleKeyboard.setKeyPadListener(this)
    }

    override fun onClickNum(num: String) {
        viewModel.input(num)
    }

    override fun onclickDelete() {
        viewModel.delete()
    }

    override fun onClickDone() {
        viewModel.done()
    }
}