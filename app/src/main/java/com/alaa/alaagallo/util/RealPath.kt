package com.alaa.alaagallo.util

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import java.io.File

object FileFromUri {
    fun getFile(context: Context, uri: Uri, isImage: Boolean): File? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10+ (Scoped Storage)
            if (isImage)
                getImageFileFromUri(context, uri)
            else
                getVideoFileFromUri(context, uri)
        } else {
            getRealPathLegacy(context, uri)?.let { File(it) } ?: if (isImage) getImageFileFromUri(
                context,
                uri
            ) else getVideoFileFromUri(context, uri)
        }
    }

    private fun getRealPathLegacy(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        return context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            if (cursor.moveToFirst()) {
                cursor.getString(columnIndex)
            } else null
        }
    }

    private fun getImageFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val file = File(context.cacheDir, "temp_${System.currentTimeMillis()}.jpg")
            file.outputStream().use { outputStream -> inputStream.copyTo(outputStream) }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getVideoFileFromUri(context: Context, videoUri: Uri): File {
        val contentResolver = context.contentResolver

        val inputStream = contentResolver.openInputStream(videoUri)
        val tempFile = File(context.cacheDir, "temp_${System.currentTimeMillis()}.mp4")

        inputStream?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return tempFile
    }
}