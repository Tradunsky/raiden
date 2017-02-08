package ua.uc.raiden

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit


fun main(args: Array<String>) {
    val testCaseFilePath = "/home/o111o1oo/dev/code/pets/raiden/tsung-wapper/src/main/resources/http_xauth.xml"
    val logDir = "/home/o111o1oo/dev/code/pets/raiden/tsung-wapper/log"
    val tsungProcess = ProcessBuilder("tsung", "-f", testCaseFilePath, "-l", logDir, "start")
            .redirectErrorStream(true)
            .start()
    watch(tsungProcess)
    tsungProcess.waitFor(2, TimeUnit.SECONDS)
}

private fun watch(process: Process) {
    object : Thread() {
        override fun run() {
            BufferedReader(InputStreamReader(process.inputStream)).use {
                it.lines().forEach(::println)
            }
        }
    }.start()
}