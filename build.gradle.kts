buildscript {

    repositories {
        // For Spoon snapshot, until 2.0.0 is released
        maven(url = "https://oss.jfrog.org/artifactory/oss-snapshot-local/")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
        jcenter()
        google()
    }

    dependencies {
        // https://github.com/gradle/kotlin-dsl/issues/1291
        classpath(GradlePlugins.androidGradle)

        // Kotlin Grade plugin
        classpath(GradlePlugins.kotlin)

        // Build Tool to generate Kotlin KDoc documentation
        classpath(GradlePlugins.dokka)

        classpath(GradlePlugins.mavenPublish)

        // Spoon Gradle Plugin
        classpath(GradlePlugins.spoon)
    }
}

allprojects {
    repositories {
        jcenter()
        google()

        // For Spoon snapshot, until 2.0.0 is released
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    }
}

tasks.create<Delete>("clean") {
    delete(rootProject.buildDir)
}
