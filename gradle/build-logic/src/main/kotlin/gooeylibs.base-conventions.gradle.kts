import gradle.kotlin.dsl.accessors._568043db04a5759fa5cb10ceea5df0d0.publishing

plugins {
    `java-library`
    kotlin("jvm")

    id("org.cadixdev.licenser")
    id("net.kyori.indra")
    id("net.kyori.indra.git")
    id("com.github.johnrengelman.shadow")
    id("maven-publish")
}

configurations {
    create("commonJava")
    create("commonResources")
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

// Declare capabilities on the outgoing configurations.
// Read more about capabilities here: https://docs.gradle.org/current/userguide/component_capabilities.html#sec:declaring-additional-capabilities-for-a-local-component
listOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements").forEach { variant ->
    val configuration = configurations.maybeCreate(variant)
    val minecraft = rootProject.property("minecraft")

    configuration.outgoing.capability("$group:${base.archivesName.get()}:$version")
    configuration.outgoing.capability("$group:gooeylibs-${project.name}-${minecraft}:$version")
    configuration.outgoing.capability("$group:gooeylibs:$version")

    publishing.publications.forEach {
        (it as MavenPublication).suppressPomMetadataWarningsFor(variant)
    }
}