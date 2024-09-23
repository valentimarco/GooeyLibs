plugins {
    id("gooeylibs.base-conventions")
    id("maven-publish")
    id("net.neoforged.moddev") version "1.0.11"
}

sourceSets {
    main {
        java.srcDirs(
            "src/mixins/java"
        )
        resources.srcDirs(
            "src/mixins/resources"
        )
    }
}

neoForge {
    neoFormVersion = "1.21-20240613.152323"
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.mixins.base)
    compileOnly(libs.mixins.extras)

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
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
        create<MavenPublication>("api") {
            from(components["java"])
            groupId = "ca.landonjw.gooeylibs"
            artifactId = "api"
            version = rootProject.version.toString()
        }
    }
}
