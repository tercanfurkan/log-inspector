package com.tercanfurkan.loginspector.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils {
    const val DEFAULT_DATE_REGEX = "\\d{2}-\\d{2}-\\d{4}"
    const val DEFAULT_DATE_FORMAT = "dd-MM-yyyy"

    fun extractDate(
        text: String,
        dateRegexPattern: String = DEFAULT_DATE_REGEX,
        dateFormatString: String = DEFAULT_DATE_FORMAT
    ): LocalDate {
        val dateAsString = dateRegexPattern.toRegex().find(text)!!.value
        val formatter = DateTimeFormatter.ofPattern(dateFormatString)
        return LocalDate.parse(dateAsString, formatter)
    }
}