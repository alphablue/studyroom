package com.example.portfolio.ui.screen.util

object TextUtil {
    fun isValidEmail(email: String): Boolean {
        val regexOption = """^([\w.\-]+)@([\w\-]+)((\.(\w){2,3})+)$""".toRegex()

        return regexOption matches email
    }

    fun isValidPassword(password: String): Boolean {
        val regexOption = """^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$""".toRegex()

        return regexOption matches password
    }
}