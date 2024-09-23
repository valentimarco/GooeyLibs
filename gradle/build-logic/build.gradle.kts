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
    implementation(libs.kotlin.jvm)
    implementation(libs.loom)
    implementation(libs.indra)
    implementation(libs.licenser)
    implementation(libs.shadow)

    implementation(libs.loom)
    implementation(libs.neo.moddev)
}