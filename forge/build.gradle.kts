plugins {
    id("gooeylibs.platform")
}

val minecraft = rootProject.property("minecraft")
val forge = rootProject.property("forge")
version = "$minecraft-$forge-${rootProject.version}"

architectury {
    platformSetupLoomIde()
    forge()
}

loom {
    forge {
        convertAccessWideners.set(true)
    }
}

dependencies {
    forge("net.minecraftforge:forge:${rootProject.property("minecraft")}-${rootProject.property("forge")}")

    implementation(project(":api", configuration = "namedElements"))
    "developmentForge"(project(":api", configuration = "namedElements"))
    bundle(project(":api", configuration = "transformProductionForge"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks {
    remapJar {
        archiveBaseName.set("GooeyLibs-Forge")
        archiveClassifier.set("")
        archiveVersion.set("$forge-${rootProject.version}")

        dependsOn("shadowJar")
        inputFile.set(named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar").flatMap { it.archiveFile })
    }

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
            from(components["java"])
            groupId = "ca.landonjw.gooeylibs"
            artifactId = "forge"
            version = rootProject.version.toString()
        }
    }
}