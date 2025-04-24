package cat.itb.damv.m78.dbdemocamera

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

@Composable
fun VistaDeCamera(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    modifier: Modifier = Modifier
) {
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var previewView: PreviewView? = null

    AndroidView(
        factory = { ctx ->
            val view = PreviewView(ctx).apply {

            }
            previewView = view
            setupCamera(context, lifecycleOwner, view, cameraProviderFuture)
            view
        },
        modifier = modifier,
        update = { view ->
            if (previewView != view) {
                previewView = view
            }
        }
    )
    DisposableEffect(key1 = lifecycleOwner) {
        onDispose {
            try {
                cameraProviderFuture.get()?.unbindAll()
            } catch (exc: Exception) {

            }
        }
    }
}
private fun setupCamera(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
    cameraProviderFuture: com.google.common.util.concurrent.ListenableFuture<ProcessCameraProvider>
) {
    cameraProviderFuture.addListener({
        try {
            val cameraProvider = cameraProviderFuture.get()
            val previewUseCase = Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }
            val imageCaptureUseCase: ImageCapture=ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                previewUseCase,
                imageCaptureUseCase
            )
        } catch (exc: Exception) {

        }
    }, ContextCompat.getMainExecutor(context))
}