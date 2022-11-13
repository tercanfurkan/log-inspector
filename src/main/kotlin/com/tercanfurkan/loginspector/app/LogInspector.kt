package com.tercanfurkan.loginspector.app

import com.tercanfurkan.loginspector.utils.DateUtils
import com.tercanfurkan.loginspector.utils.FileUtils
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import java.util.*

/**
 * LogInspector API, with its {@link #countEntries countEntries} method
 * it maps log file names TO the number of lines which contains the given search query
 *
 * @param dateRegex date regex pattern on the log file name
 * @param dateFormat date format string on the log file name
 */
class LogInspector(
    val dateRegex: String = DateUtils.DEFAULT_DATE_REGEX,
    val dateFormat: String = DateUtils.DEFAULT_DATE_FORMAT
) {
    companion object {
        val TEMP_DIR: String = System.getProperty("java.io.tmpdir")
    }

    fun countEntries(
        startDate: LocalDate, endDate: LocalDate, searchQuery: String, zipFile: File
    ): Map<String, Int> {
        val targetDir = File(TEMP_DIR, UUID.randomUUID().toString())
        FileUtils.unzip(zipFile, targetDir)
        return Files.walk(targetDir.toPath())
            .toList()
            .filter { filePath ->
                Files.isRegularFile(filePath) && isFileDateInRange(
                    filePath, startDate, endDate
                )
            }
            .map { path ->
                path.toFile()
            }
            .associate { file ->
                file.name to file.readLines().count { it.contains(searchQuery) }
            }
    }

    private fun isFileDateInRange(pathToFileWithDate: Path, startDate: LocalDate, endDate: LocalDate): Boolean {
        val fileDate = DateUtils.extractDate(pathToFileWithDate.toFile().name, )
        return fileDate in startDate..endDate
    }
}