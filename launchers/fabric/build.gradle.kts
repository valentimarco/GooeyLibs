plugins {
    id("gooeylibs.base-conventions")
    id("fabric-loom") version "1.7-SNAPSHOT"
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())

    include(implementation(projects.api)!!)
    modImplementation(libs.fabric.loader)

    setOf(
        "fabric-lifecycle-events-v1",
        "fabric-command-api-v2"
    ).forEach { modImplementation(fabricApi.module(it, "0.103.0+1.21.1")) }
}

tasks {
    processResources {
        val version: String = rootProject.property("modVersion") as String
        inputs.property("version", version)

        filesMatching("fabric.mod.json") {
            expand("version" to version)
        }
    }
}