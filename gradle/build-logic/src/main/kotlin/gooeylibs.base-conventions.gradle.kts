plugins {
    `java-library`
    kotlin("jvm")

    id("org.cadixdev.licenser")
    id("net.kyori.indra")
    id("net.kyori.indra.git")
    id("com.github.johnrengelman.shadow")
}

indra {
    javaVersions {
        minimumToolchain(21)
        target(21)
    }
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    withType<Test> {
        useJUnitPlatform()
        jvmArgs("-Djunit.jupiter.extensions.autodetection.enabled=true")
    }
}

license {
    header(rootProject.file("HEADER.txt"))
    properties {
        this.set("name", "GooeyLibs")
        this.set("years", "201x - 2024")
    }
}