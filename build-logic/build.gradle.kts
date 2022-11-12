plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven("https://maven.architectury.dev/")
    maven("https://maven.fabricmc.net/")
    maven("https://maven.minecraftforge.net/")
}

dependencies {
    implementation(libs.kotlin)
    implementation(libs.loom)
    implementation(libs.architecturyPlugin)
    implementation(libs.shadow)
}