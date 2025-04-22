package cat.itb.damv.m78.dbdemocamera

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "camerademo2",
    ) {
        App()
    }
}