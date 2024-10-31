plugins {
    alias(libs.plugins.loom)
    alias(libs.plugins.indra)
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    compileOnly(libs.fabric.loader)
}

loom {
    mixin {
        useLegacyMixinAp = false
    }
    runtimeOnlyLog4j.set(true)
}

tasks {
    val api = project(":api")
    jar {
        from(zipTree(api.tasks.jar.flatMap { it.archiveFile })) {
            exclude("META-INF/MANIFEST.MF")
        }
        manifest {
            attributes("Fabric-Loom-Remap" to true)
        }
    }

    sourcesJar {
        from(zipTree(api.tasks.sourcesJar.flatMap { it.archiveFile }))
    }

    javadoc {
        enabled = false
    }
}