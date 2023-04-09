import net.fabricmc.loom.configuration.ide.RunConfig
import net.fabricmc.loom.configuration.ide.RunConfigSettings

plugins {
    id("gooeylibs.platform")
}

val minecraft = rootProject.property("minecraft")
val fabric = rootProject.property("fabric-api")
version = "$minecraft-$fabric-${rootProject.version}"

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    accessWidenerPath.set(project(":api").file(ACCESS_WIDENER))

    runs {
        val server = maybeCreate("server")
        server.vmArgs.add("-Dmixin.debug.export=true")
    }
}

val generatedResources = file("src/generated/resources")
sourceSets {
    main {
        resources {
            srcDir(generatedResources)
        }
    }
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${rootProject.property("fabric-loader")}")
    modImplementation(fabricApi.module("fabric-lifecycle-events-v1", "0.75.1+1.19.2"))
    modImplementation(fabricApi.module("fabric-command-api-v2", "0.75.1+1.19.2"))

    implementation(project(":api", configuration = "namedElements"))
    "developmentFabric"(project(":api", configuration = "namedElements"))
    bundle(project(":api", configuration = "transformProductionFabric"))
}

tasks {
    val copyAccessWidener by registering(Copy::class) {
        from(loom.accessWidenerPath)
        into(generatedResources)
    }

    processResources {
        dependsOn(copyAccessWidener)
        inputs.property("version", rootProject.version)

        filesMatching("fabric.mod.json") {
            expand("version" to rootProject.version)
        }
    }

}

publishing {
    repositories {
        maven("https://maven.impactdev.net/repository/development/") {
            name = "ImpactDev-Public"
            credentials {
                username = System.getenv("NEXUS_USER")
                password = System.getenv("NEXUS_PW")
            }
        }
    }

    publications {
        create<MavenPublication>("fabric") {
            from(components["java"])
            groupId = "ca.landonjw.gooeylibs"
            artifactId = "fabric"

            val minecraft = rootProject.property("minecraft")
            val snapshot = rootProject.property("snapshot")?.equals("true") ?: false
            val project = rootProject.property("modVersion")
            version = "$project-$minecraft${if(snapshot) "-SNAPSHOT" else ""}"
        }
    }
}