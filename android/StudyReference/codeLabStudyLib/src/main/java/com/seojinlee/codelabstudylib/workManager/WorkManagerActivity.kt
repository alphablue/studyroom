package com.seojinlee.codelabstudylib.workManager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.seojinlee.codelabstudylib.R
import com.seojinlee.codelabstudylib.databinding.ActivityBlurWorkManagerBinding

class WorkManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBlurWorkManagerBinding
    private val viewModel: BlurViewModel by viewModels{
        BlurViewModel.BlurViewModelFactory(
            application
        )
    }

    // fragment-ktx 를 사용하면 가능함
//    private val ktxViewModel: BlurViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_blur_work_manager)

        binding.goButton.setOnClickListener { viewModel.applyBlur(blurLevel) }
    }

    private fun showWorkInProgress() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            cancelButton.visibility = View.VISIBLE
            goButton.visibility = View.GONE
            seeFileButton.visibility = View.GONE
        }
    }

    private fun showWorkFinished() {
        with(binding) {
            progressBar.visibility = View.GONE
            cancelButton.visibility = View.GONE
            goButton.visibility = View.VISIBLE
        }
    }

    private val blurLevel: Int
        get() =
            when (binding.radioBlurGroup.checkedRadioButtonId) {
                R.id.radio_blur_lv_1 -> 1
                R.id.radio_blur_lv_2 -> 2
                R.id.radio_blur_lv_3 -> 3
                else -> 1
            }
}