plugins {
    id("gooeylibs.base-conventions")
    id("net.neoforged.moddev") version "1.0.11"
}

neoForge {
    version = "21.1.59"
    validateAccessTransformers = true

    val client = runs.create("client")
    client.client()

    val gooey = mods.maybeCreate("gooeylibs")
    gooey.sourceSet(sourceSets.main.get())
}

dependencies {
    jarJar(implementation(projects.api)!!)
}

tasks {
    processResources {
        val version: String = rootProject.property("modVersion") as String
        inputs.property("version", version)

        filesMatching("META-INF/neoforge.mods.toml") {
            expand("version" to version)
        }
    }
}
