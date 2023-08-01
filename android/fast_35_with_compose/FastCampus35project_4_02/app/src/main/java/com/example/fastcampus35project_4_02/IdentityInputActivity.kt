package com.example.fastcampus35project_4_02

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.example.fastcampus35project_4_02.databinding.ActivityIndentityInputBinding
import com.example.fastcampus35project_4_02.util.Viewutil.hideKeyboard
import com.example.fastcampus35project_4_02.util.Viewutil.setOnEditorActionListener
import com.example.fastcampus35project_4_02.util.Viewutil.showKeyboard
import com.example.fastcampus35project_4_02.util.Viewutil.showKeyboardDelay

class IdentityInputActivity: AppCompatActivity() {
    private lateinit var binding: ActivityIndentityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_indentity_input)
        binding.view = this

        initView()
        binding.nameEdit.showKeyboardDelay()
    }

    private fun initView() {
        with(binding) {
            nameLayout.isVisible = true

            nameEdit.setOnEditorActionListener(EditorInfo.IME_ACTION_NEXT) {
                birthdayLayout.isVisible = true
                birthdayEdit.showKeyboard()
            }

            birthdayEdit.doAfterTextChanged {
                if(birthdayEdit.length() > 7) {
                    genderLayout.isVisible = true
                    birthdayEdit.hideKeyboard()
                }
            }

            genderChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                if(!telecomLayout.isVisible) {
                    telecomLayout.isVisible = true
                }
            }

            telecomChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                if(!phoneLayout.isVisible) {
                    phoneLayout.isVisible = true
                    phoneEdit.showKeyboard()
                }
            }

            phoneEdit.doAfterTextChanged {
                if(phoneEdit.length() > 10) {
                    confirmButton.isVisible = true
                    phoneEdit.hideKeyboard()
                }
            }

            phoneEdit.setOnEditorActionListener(EditorInfo.IME_ACTION_DONE) {
                if(phoneEdit.length() > 9) {
                    confirmButton.isVisible = true
                    phoneEdit.hideKeyboard()
                }
            }
        }
    }

    fun onClickDone() {
        if(!validName()) {
            binding.nameLayout.error = "1자 이상의 한글을 입력해주세요."
            return
        }

        if(!validBirthday()) {
            binding.birthdayLayout.error = "생년월일 형식이 다릅니다."
            return
        }

        if(!validPhone()) {
            binding.phoneLayout.error = "전화번호 형식이 다릅니다."
            return
        }
    }

    private fun validName() = !binding.nameEdit.text.isNullOrBlank()
            && REGEX_NAME.toRegex().matches(binding.nameEdit.text!!)

    private fun validBirthday() = !binding.birthdayEdit.text.isNullOrBlank()
            && REGEX_BIRTHDAY.toRegex().matches(binding.birthdayEdit.text!!)

    private fun validPhone() = !binding.phoneEdit.text.isNullOrBlank()
            && REGEX_PHONE.toRegex().matches(binding.phoneEdit.text!!)

    companion object {
        private const val REGEX_NAME = "^[가-힣]{2,}\$"
        private const val REGEX_BIRTHDAY = "^(19|20)[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])"
        private const val REGEX_PHONE = "^01([016789])([0-9]{3,4})([0-9]{4})"
    }
}