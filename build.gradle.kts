plugins {
    id("java")
    id("java-library")
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("dev.architectury.loom") version "0.12.0-SNAPSHOT"
    id("maven-publish")
}

architectury {
    platformSetupLoomIde()
    forge()
}

dependencies {
    val loom = project.extensions.getByName<net.fabricmc.loom.api.LoomGradleExtensionAPI>("loom")
    loom.silentMojangMappingsLicense()

    minecraft("com.mojang:minecraft:${rootProject.property("minecraft")}")
    mappings(loom.officialMojangMappings())
    forge("net.minecraftforge:forge:${rootProject.property("minecraft")}-${rootProject.property("forge")}")
}

// Example for how to get properties into the manifest for reading by the runtime..
//jar {
//    manifest {
//        attributes([
//                "Specification-Title": modBaseName,
//                "Specification-Vendor": "landonjw",
//                "Specification-Version": "1", // We are version 1 of ourselves
//                "Implementation-Title": project.name,
//                "Implementation-Version": "${version}",
//                "Implementation-Vendor" :"landonjw",
//                "Implementation-Timestamp": new Date().format("yyyy-MM-dd'T'HH:mm:ssZ")
//        ])
//    }
//}

//sourceSets.main.resources { srcDir 'src/generated/resources' }

//publishing {
//    repositories {
//        maven {
//            name = "ImpactDev-Public"
//            url = "https://maven.impactdev.net/repository/development/"
//            credentials {
//                username System.getenv('NEXUS_USER')
//                password System.getenv('NEXUS_PW')
//            }
//        }
//    }
//
//    publications {
//        mavenJava(MavenPublication) {
//            artifactId "${project.modBaseName}"
//            version "${project.mcversion}-${version}"
//            artifact jar
//        }
//    }
//}