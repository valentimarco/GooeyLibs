plugins {
    id("gooeylibs.base-conventions")
    id("maven-publish")
}

architectury {
    common("forge", "fabric")
}

loom {
    val ACCESS_WIDENER = "src/main/resources/gooeylibs.accesswidener"

    accessWidenerPath.set(file(ACCESS_WIDENER))
    silentMojangMappingsLicense()
}

val commonManifest = java.manifest {
    attributes(
        "Specification-Title" to "GooeyLibs",
        "Specification-Version" to project.version,
        "Implementation-Title" to project.name,
        "Implementation-Version" to project.version,
        "Implementation-Vendor" to "GooeyLibs"
    )
    // These two are included by most CI's
    System.getenv()["GIT_COMMIT"]?.apply { attributes("Git-Commit" to this) }
    System.getenv()["GIT_BRANCH"]?.apply { attributes("Git-Branch" to this) }
}

val main by sourceSets

val mixins by sourceSets.registering {
    compileClasspath += main.compileClasspath
    runtimeClasspath += main.runtimeClasspath
    dependencies.add(implementationConfigurationName, main.output)
}

val bootstrapConfig by configurations.register("bootstrap") {
    extendsFrom(configurations.modImplementation.get())
}

val bootstrap by sourceSets.registering {
    compileClasspath += main.compileClasspath
    runtimeClasspath += main.runtimeClasspath
    dependencies.add(implementationConfigurationName, main.output)

    configurations.named(implementationConfigurationName) {
        extendsFrom(bootstrapConfig)
    }
}

sourceSets.configureEach {
    val sourceSet = this
    val isMain = "main" == sourceSet.name

    if (!isMain) {
        val sourcesJarName: String = (sourceSet.name + "SourcesJar")
        tasks.register(sourcesJarName, Jar::class.java) {
            group = "build"
            val classifier = if (isMain) "sources" else (sourceSet.name + "-sources")
            archiveClassifier.set(classifier)
            from(sourceSet.allJava)
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    modImplementation(libs.fabric.loader)

    bootstrapConfig(fabricApi.module("fabric-lifecycle-events-v1", "0.103.0+1.21.1"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks {
    val mixinsJar by registering(Jar::class) {
        archiveClassifier.set("mixins")
        manifest.from(commonManifest)
        from(mixins.map { it.output })
    }

    shadowJar {
        val jar by existing

        mergeServiceFiles()
        archiveClassifier.set("dev")
        manifest {
            from(commonManifest)
        }

        from(jar)
        from(mixinsJar)
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
        create<MavenPublication>("api") {
            from(components["java"])
            groupId = "ca.landonjw.gooeylibs"
            artifactId = "api"
            version = rootProject.version.toString()
        }
    }
}
