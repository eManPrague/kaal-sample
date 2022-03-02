buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        // https://github.com/gradle/kotlin-dsl/issues/1291
        classpath(GradlePlugins.androidGradle)
        // Kotlin Grade plugin
        classpath(GradlePlugins.kotlin)
        // Build Tool to generate Kotlin KDoc documentation
        classpath(GradlePlugins.dokka)
        classpath(GradlePlugins.mavenPublish)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        // Kaal and eMan libs
        maven(url = "https://nexus.eman.cz/repository/maven-public")
    }
}

tasks.create<Delete>("clean") {
    delete(rootProject.buildDir)
}
