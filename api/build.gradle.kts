plugins {
    id("gooeylibs.api")
    id("maven-publish")
}

architectury {
    common("forge", "fabric")
}

loom {
    accessWidenerPath.set(project(":api").file(ACCESS_WIDENER))
}

repositories {
    mavenCentral()
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${rootProject.property("fabric-loader")}")

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