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

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${rootProject.property("fabric-loader")}")
    modImplementation(fabricApi.module("fabric-lifecycle-events-v1", "0.64.0+1.19.2"))
    modImplementation(fabricApi.module("fabric-command-api-v2", "0.64.0+1.19.2"))

    implementation(project(":api", configuration = "namedElements"))
    "developmentFabric"(project(":api", configuration = "namedElements"))
    bundle(project(":api", configuration = "transformProductionFabric"))
}

tasks {
    remapJar {
        archiveBaseName.set("GooeyLibs-Fabric")
        archiveClassifier.set("")
        archiveVersion.set("$fabric-${rootProject.version}")

        dependsOn("shadowJar")
        inputFile.set(named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar").flatMap { it.archiveFile })
    }

    processResources {
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
            version = rootProject.version.toString()
        }
    }
}