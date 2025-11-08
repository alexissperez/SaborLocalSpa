package com.example.saborlocalspa.utils

import android.content.Context
import android.net.Uri


fun saveImageToInternalStorage(context: Context, uri: Uri): String {
    val fileName = "profile_pic.jpg"
    val inputStream = context.contentResolver.openInputStream(uri)
    val outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }
    return context.filesDir.resolve(fileName).absolutePath
}
object ImageStorageUtil {
    fun saveAvatarUri(context: Context, uri: Uri) {
        val prefs = context.getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("avatar_uri", uri.toString()).apply()
    }

    fun loadAvatarUri(context: Context): Uri? {
        val prefs = context.getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)
        val uriString = prefs.getString("avatar_uri", null)
        return uriString?.let { Uri.parse(it) }
    }
}