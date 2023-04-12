plugins {
    id("gooeylibs.platform")
}

val minecraft = rootProject.property("minecraft")
val forge = rootProject.property("forge")

architectury {
    platformSetupLoomIde()
    forge()
}

loom {
    accessWidenerPath.set(project(":api").file(ACCESS_WIDENER))
    forge {
        convertAccessWideners.set(true)
    }
}

dependencies {
    forge("net.minecraftforge:forge:$minecraft-$forge")

    implementation(project(":api", configuration = "namedElements"))
    "developmentForge"(project(":api", configuration = "namedElements"))
    bundle(project(":api", configuration = "transformProductionForge"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks {
    processResources {
        inputs.property("version", rootProject.version)

        filesMatching("META-INF/mods.toml") {
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
        create<MavenPublication>("forge") {
            artifact(tasks.remapJar)
            artifact(tasks.remapSourcesJar)

            groupId = "ca.landonjw.gooeylibs"
            artifactId = "forge"
            version = rootProject.version.toString()
        }
    }
}