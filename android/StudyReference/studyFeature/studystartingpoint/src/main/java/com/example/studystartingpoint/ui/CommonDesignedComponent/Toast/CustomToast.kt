package com.example.studystartingpoint.ui.CommonDesignedComponent.Toast

import android.content.Context
import android.widget.Toast

fun makeToastShort(
    context: Context,
    message: String
) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}