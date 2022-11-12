plugins {
    base
    id("gooeylibs.root")
}

group = project.group
version = project.version

allprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")

    repositories {
        mavenCentral()
    }
}