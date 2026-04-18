package dev.deftu.kdlflattener

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.gradle.language.jvm.tasks.ProcessResources

class KdlFlattenerPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.withType<ProcessResources>().configureEach {
            filesMatching("**/*.flatten.kdl") {
                path = path.replace(".flatten.kdl", ".json")
                filter(KdlFlattenReader::class.java)
            }
        }
    }
}
