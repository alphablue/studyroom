package com.example.composestudymodule.tutorial

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.toss.next.testapp.ui.theme.TutorialTheme

@Composable
fun UiButton(onButtonClicked: () -> Unit) {
//    Button(onClick = { onButtonClicked() }) {
//        Text(text = "send")
//    }

    // 아이콘이 있는 버튼
//    Button(onClick = { onButtonClicked() }) {
//        Icon(
//            imageVector = Icons.Filled.Send,
//            contentDescription = null
//        )
//        Text(text = "send")
//    }

//    Button(onClick = { onButtonClicked() }) {
//        Icon(
//            imageVector = Icons.Filled.Send,
//            contentDescription = null
//        )
//        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
//        Text(text = "send")
//    }

    // 클릭 비활성화
    Button(onClick = { onButtonClicked() }, enabled = false) {
        Icon(
            imageVector = Icons.Filled.Send,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
        Text(text = "send")
    }

}

@Preview(showBackground = true)
@Composable
fun UiButtonPreview() {
    TutorialTheme {
        UiButton {

        }
    }
}