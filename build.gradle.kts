plugins {
    kotlin("jvm") version(libs.versions.kotlin)
    `kotlin-dsl`

    with(libs.versions.dgt) {
        id("dev.deftu.gradle.tools.repo") version(this)
        id("dev.deftu.gradle.tools.configure") version(this)
        id("dev.deftu.gradle.tools.publishing.maven") version(this)
        id("dev.deftu.gradle.tools.publishing.github") version(this)
    }
}

toolkitMavenPublishing {
    setupPublication.set(false)
}

repositories {
    maven("https://jitpack.io/")
    maven("https://maven.fabricmc.net/")
    maven("https://maven.minecraftforge.net/")
    maven("https://maven.architectury.dev/")
    maven("https://maven.jab125.dev/")

    mavenCentral()
    gradlePluginPortal()
    mavenLocal()
}

dependencies {
    // Language
    implementation(gradleApi())

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.github.kdl-org:kdl4j:1.0.1")
}

gradlePlugin {
    plugins {
        register("kdl-flattener") {
            id = "dev.deftu.gradle.kdl-flattener"
            implementationClass = "dev.deftu.kdlflattener.KdlFlattenerPlugin"
        }
    }
}

tasks {
    named<Jar>("jar") {
        from("LICENSE")

        manifest {
            attributes["Implementation-Version"] = version
        }
    }
}
