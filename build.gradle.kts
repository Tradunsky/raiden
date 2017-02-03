buildscript {
    repositories { gradleScriptKotlin() }
    dependencies { classpath(kotlinModule("gradle-plugin")) }
}

plugins { application }

apply {
    plugin("kotlin")
    from("env.default.kts")
}

repositories {
    gradleScriptKotlin()
}

dependencies {
    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        archives(it)
    }
}

tasks{
    "tsung" {
        "echo hello".runCommand(project.file(""))
    }
}

fun String.runCommand(workingDir: java.io.File): String? {
    try {
        val parts = this.split("\\s".toRegex())
        val proc = java.lang.ProcessBuilder(*parts.toTypedArray())
                .directory(workingDir)
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start()

        proc.waitFor(60, java.util.concurrent.TimeUnit.MINUTES)
        return proc.inputStream.bufferedReader().readText()
    } catch(e: java.io.IOException) {
        e.printStackTrace()
        return null
    }
}