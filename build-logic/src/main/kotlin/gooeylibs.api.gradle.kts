plugins {
    id("java")
    id("java-library")
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("maven-publish")
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

architectury {
    minecraft = project.property("minecraft").toString()
}

loom {
    silentMojangMappingsLicense()
    accessWidenerPath.set(project(":api").file(ACCESS_WIDENER))

    mixin {
        defaultRefmapName.set("mixins.gooeylibs.${project.name}.refmap.json")
    }
}

dependencies {
    minecraft("net.minecraft:minecraft:${rootProject.property("minecraft")}")
    mappings(loom.officialMojangMappings())
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    withType<Jar> {
        from(rootProject.file("LICENSE"))
    }
}