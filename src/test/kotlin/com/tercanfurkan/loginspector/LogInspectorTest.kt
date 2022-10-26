package com.tercanfurkan.loginspector

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.nio.file.Paths
import java.time.LocalDate

private const val zipFileName = "/23-10-2022_26-10-2022-output.zip"

class LogInspectorTest {
    private val logInspector = LogInspector()
    private val file = Paths.get(javaClass.getResource(zipFileName)!!.toURI()).toFile()

    @Test
    fun `gets day keys correctly based on dates`() {
        val fileToCountMap: Map<String, Int> = logInspector.countEntries(
            LocalDate.of(2022, 10, 24), LocalDate.of(2022, 10, 25), "RANDOM TEXT", file
        )

        assertThat(fileToCountMap.size).isEqualTo(2)
        assertThat(fileToCountMap).containsKey("24-10-2022-output.log")
        assertThat(fileToCountMap).containsKey("25-10-2022-output.log")
    }

    @Test
    fun `counts lines with MQTT correctly`() {
        val fileToCountMap = logInspector.countEntries(
            LocalDate.of(2022, 10, 23), LocalDate.of(2022, 10, 25), "MQTT", file
        )

        assertThat(fileToCountMap.size).isEqualTo(3)
        assertThat(fileToCountMap["23-10-2022-output.log"]).isEqualTo(2)
        assertThat(fileToCountMap["24-10-2022-output.log"]).isEqualTo(3)
        assertThat(fileToCountMap["25-10-2022-output.log"]).isEqualTo(6)
    }

    @Test
    fun `counts lines with WEBRTC correctly`() {
        val fileToCountMap = logInspector.countEntries(
            LocalDate.of(2022, 10, 24), LocalDate.of(2022, 10, 26), "WEBRTC", file
        )

        assertThat(fileToCountMap.size).isEqualTo(3)
        assertThat(fileToCountMap["24-10-2022-output.log"]).isEqualTo(3)
        assertThat(fileToCountMap["25-10-2022-output.log"]).isEqualTo(5)
        assertThat(fileToCountMap["26-10-2022-output.log"]).isEqualTo(7)
    }
}