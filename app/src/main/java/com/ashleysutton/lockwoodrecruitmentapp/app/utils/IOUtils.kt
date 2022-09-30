package com.ashleysutton.lockwoodrecruitmentapp.app.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Util class for reading any contents of a file
 */
class IOUtils {
    companion object {
        /**
         * Read the contents of a file and return it
         *
         * @param context - Application context
         * @param fileName - Name of the file to read
         *
         * @return - String contents of the file
         */
        fun getFileContents(context: Context, fileName: String?): String? {
            val inputStream: InputStream?

            return try {
                inputStream = context.assets.open(fileName!!)
                val reader = BufferedReader(InputStreamReader(inputStream, UTF8_CHARSET))
                val stringBuilder = StringBuilder()
                var nextLine: String?
                while (reader.readLine().also { nextLine = it } != null) {
                    stringBuilder.append(nextLine).append("\n")
                }
                inputStream.close()
                stringBuilder.toString()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        private const val UTF8_CHARSET = "UTF-8"
    }
}