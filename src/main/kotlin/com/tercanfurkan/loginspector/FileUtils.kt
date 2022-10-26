package com.tercanfurkan.loginspector

import java.io.*
import java.nio.file.Path
import java.util.zip.ZipFile
import kotlin.properties.Delegates

object FileUtils {
    /**
     * Size of the buffer to read/write data
     */
    private const val BUFFER_SIZE = 4096

    /**
     * Unzip a file to a location
     */
    @Throws(IOException::class) fun unzip(zipFilePath: File, unzipLocation: File) {
        unzipLocation.run {
            if (!exists()) {
                mkdirs()
            }
        }

        ZipFile(zipFilePath).use { zip ->
            zip.entries().asSequence().forEach { entry ->
                zip.getInputStream(entry).use { input ->
                    val filePath = unzipLocation.path + File.separator + entry.name
                    if (!entry.isDirectory) {
                        // if the entry is a file, extracts it
                        extractFile(input, filePath)
                    } else {
                        // if the entry is a directory, make the directory
                        val dir = File(filePath)
                        dir.mkdir()
                    }
                }
            }
        }
    }

    /**
     * Extracts a zip entry (file entry) to a destination
     */
    @Throws(IOException::class) private fun extractFile(
        inputStream: InputStream,
        destFilePath: String
    ) {
        val bufferedOutputStream = BufferedOutputStream(FileOutputStream(destFilePath))
        val bytesIn = ByteArray(BUFFER_SIZE)
        var read by Delegates.notNull<Int>()
        while (inputStream.read(bytesIn).also { read = it } != -1) {
            bufferedOutputStream.write(bytesIn, 0, read)
        }
        bufferedOutputStream.close()
    }
}