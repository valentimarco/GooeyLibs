plugins {
    id("gooeylibs.api")
    id("com.github.johnrengelman.shadow")
}

val bundle: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

tasks {
    jar {
        archiveBaseName.set("GooeyLibs-${project.name}")
        archiveClassifier.set("dev-slim")
    }

    shadowJar {
        archiveBaseName.set("GooeyLibs-${project.name}")
        archiveClassifier.set("dev-shaded")
        configurations = listOf(bundle)
    }

    remapJar {
        dependsOn(shadowJar)
        inputFile.set(shadowJar.flatMap { it.archiveFile })
        archiveBaseName.set("GooeyLibs-${project.name}")
        archiveClassifier.set("")
        archiveVersion.set("${project.version}")
    }
}