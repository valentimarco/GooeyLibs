pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.spongepowered.org/repository/maven-public/")
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net/")
    }

    includeBuild("build-logic")
}

rootProject.name = "GooeyLibs"
include("forge")
include("fabric")
include("api")
