package com.tercanfurkan.loginspector

import java.nio.file.Path
import java.time.LocalDate
import java.util.*

object DateUtils {

    private fun isWithinDates(pathToFileWithDate: Path, startDate: LocalDate, numberOfDays: Int): Boolean {
        val fileDate = extractDate(pathToFileWithDate.toFile().name)
        return fileDate in getDateRange(numberOfDays, startDate)
    }

    fun extractDate(text: String, datePatternRegex: Regex = "\\d{4}-\\d{2}-\\d{2}".toRegex()): LocalDate {
        val dateAsString = datePatternRegex.find(text)!!.value
        return LocalDate.parse(dateAsString)
    }

    fun getDateRange(numberOfDays: Int, fromDate: LocalDate): ClosedRange<LocalDate> {
        val untilDate = fromDate.plusDays(numberOfDays.toLong() - 1)
        return fromDate..untilDate
    }
}