package com.tercanfurkan.loginspector

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils {
    /**
     * Default date pattern regex
     */
    private const val DATE_REGEX_PATTERN = "\\d{2}-\\d{2}-\\d{4}"
    private const val DATE_FORMAT = "dd-MM-yyyy"

    fun extractDate(
        text: String,
        dateRegexPattern: String = DATE_REGEX_PATTERN,
        dateFormatString: String = DATE_FORMAT
    ): LocalDate {
        val dateAsString = dateRegexPattern.toRegex().find(text)!!.value
        val formatter = DateTimeFormatter.ofPattern(dateFormatString)
        return LocalDate.parse(dateAsString, formatter)
    }
}