package com.tercanfurkan.loginspector

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import java.util.*

class LogInspector {
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
            .filter { path ->
                Files.isRegularFile(path) && isWithinDates(
                    path, startDate, endDate
                )
            }
            .map { path ->
                path.toFile()
            }
            .associate { file ->
                file.name to file.readLines().count { it.contains(searchQuery) }
            }
    }

    private fun isWithinDates(pathToFileWithDate: Path, startDate: LocalDate, endDate: LocalDate): Boolean {
        val fileDate = DateUtils.extractDate(pathToFileWithDate.toFile().name)
        return fileDate in startDate..endDate
    }
}