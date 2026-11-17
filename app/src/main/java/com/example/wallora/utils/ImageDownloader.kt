package com.example.wallora.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.example.wallora.data.model.DownloadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object ImageDownloader {

    fun downloadImage(
        context: Context,
        imageUrl: String,
        title: String
    ): Flow<DownloadState> = flow {
        emit(DownloadState.Downloading(5))
        try {
            val cleanTitle = title.replace("[^a-zA-Z0-9_-]".toRegex(), "_").take(30)
            val fileName = "Wallora_${cleanTitle}_${System.currentTimeMillis()}.jpg"

            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 15000
            connection.readTimeout = 15000
            connection.connect()

            val contentLength = connection.contentLength
            val inputStream: InputStream = connection.inputStream

            emit(DownloadState.Downloading(25))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val resolver = context.contentResolver
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Wallora")
                    put(MediaStore.MediaColumns.IS_PENDING, 1)
                }

                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                if (imageUri == null) {
                    emit(DownloadState.Error("Failed to create MediaStore entry."))
                    return@flow
                }

                resolver.openOutputStream(imageUri).use { outputStream ->
                    if (outputStream == null) {
                        emit(DownloadState.Error("Failed to open output stream."))
                        return@flow
                    }

                    val buffer = ByteArray(8192)
                    var bytesRead: Int
                    var totalRead = 0L

                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                        totalRead += bytesRead
                        if (contentLength > 0) {
                            val progress = 25 + ((totalRead.toFloat() / contentLength) * 70).toInt()
                            emit(DownloadState.Downloading(progress.coerceAtMost(95)))
                        }
                    }
                    outputStream.flush()
                }

                contentValues.clear()
                contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                resolver.update(imageUri, contentValues, null, null)

                emit(DownloadState.Downloading(100))
                emit(DownloadState.Success(imageUri.toString()))

            } else {
                val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val walloraFolder = File(picturesDir, "Wallora")
                if (!walloraFolder.exists()) walloraFolder.mkdirs()

                val imageFile = File(walloraFolder, fileName)
                FileOutputStream(imageFile).use { outputStream ->
                    val buffer = ByteArray(8192)
                    var bytesRead: Int
                    var totalRead = 0L

                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                        totalRead += bytesRead
                        if (contentLength > 0) {
                            val progress = 25 + ((totalRead.toFloat() / contentLength) * 70).toInt()
                            emit(DownloadState.Downloading(progress.coerceAtMost(95)))
                        }
                    }
                    outputStream.flush()
                }

                emit(DownloadState.Downloading(100))
                emit(DownloadState.Success(imageFile.absolutePath))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            emit(DownloadState.Error(e.localizedMessage ?: "Download failed."))
        }
    }.flowOn(Dispatchers.IO)
}
