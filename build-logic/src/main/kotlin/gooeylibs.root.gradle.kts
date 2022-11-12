plugins {
    `java-library`
}

tasks {
    val collect by registering(Copy::class) {
        val tasks = subprojects.filter { it.path != ":api" }.map { it.tasks.named("remapJar") }
        dependsOn(tasks)

        from(tasks)
        into(buildDir.resolve("deploy"))
    }

    assemble {
        dependsOn(collect)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}