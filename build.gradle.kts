plugins {
    base
    id("gooeylibs.root")
}

group = project.group
version = "${project.property("modVersion")}-${project.property("minecraft")}"

val isSnapshot = project.property("snapshot")?.equals("true") ?: false
if (isSnapshot) {
    version = "$version-SNAPSHOT"
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")

    repositories {
        mavenCentral()
    }
}