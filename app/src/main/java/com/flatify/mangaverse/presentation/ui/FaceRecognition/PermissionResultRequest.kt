package com.flatify.mangaverse.presentation.ui.FaceRecognition

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class PermissionResultRequest(
    private val launcher: ActivityResultLauncher<String>
) {
    private var onResult: ((Boolean) -> Unit)? = null

    fun launch(permission: String) {
        launcher.launch(permission)
    }

    operator fun invoke(callback: (Boolean) -> Unit): PermissionResultRequest {
        onResult = callback
        return this
    }

    fun onPermissionResult(isGranted: Boolean) {
        onResult?.invoke(isGranted)
    }
}