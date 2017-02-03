package ua.uc.raiden

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit


fun main(args: Array<String>) {
    val testCaseFilePath = "/home/o111o1oo/dev/code/pets/raiden/tsung-wapper/src/main/resources/http_simple.xml"
    val tsungProcess = ProcessBuilder("tsung", "start", "-f", testCaseFilePath)
            .redirectErrorStream(true)
            .start()
    watch(tsungProcess)
    tsungProcess.waitFor(2, TimeUnit.SECONDS)
}

private fun watch(process: Process) {
    object : Thread() {
        override fun run() {
            val input = BufferedReader(InputStreamReader(process.inputStream))
            input.lines().forEach { println(it) }
        }
    }.start()
}