plugins {
    id("gooeylibs.loader-conventions")
    alias(libs.plugins.loom)
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())

    modImplementation(libs.fabric.loader)

    setOf(
        "fabric-lifecycle-events-v1",
        "fabric-command-api-v2"
    ).forEach { modImplementation(fabricApi.module(it, "0.103.0+1.21.1")) }

    // API Inclusion
    api(project(":launchers:fabric:api-repack", configuration = "namedElements"))
    include(projects.launchers.fabric.apiRepack)
}

tasks {
    val version: String = rootProject.property("modVersion") as String
    processResources {
        inputs.property("version", version)

        filesMatching("fabric.mod.json") {
            expand("version" to version)
        }
    }

    remapJar {
        archiveBaseName.set("GooeyLibs-Fabric")
        archiveVersion.set(version)
    }
}