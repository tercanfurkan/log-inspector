# log-inspector

A gradle (7.3) project which uses kotlin.jvm 1.6.21

LogInspector API which maps log file names TO the number of lines which contains the given search query

## Sample


### ZIP file "23-10-2022_26-10-2022-output.zip":
ZIP archive contains:
```
23-10-2022-output.log
24-10-2022-output.log
25-10-2022-output.log
26-10-2022-output.log
```

### Using the API
```kotlin
val logInspector = LogInspector()
val searchQuery = "MQTT"
val zipFileName = "23-10-2022_26-10-2022-output.zip"
val zipFile: File = Paths.get(javaClass.getResource(zipFileName)!!.toURI()).toFile()
val fileNameToLineCountMap = logInspector.countEntries(LocalDate.of(2022, 10, 23), LocalDate.of(2022, 10, 25), "MQTT", zipFile)
```

### Output
```json
{
  "23-10-2022-output.log" : 2,
  "24-10-2022-output.log" : 3,
  "25-10-2022-output.log" : 6
}
```
