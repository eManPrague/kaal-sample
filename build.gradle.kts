buildscript {

    repositories {
        // For Spoon snapshot, until 2.0.0 is released
        maven(url = "https://oss.jfrog.org/artifactory/oss-snapshot-local/")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
        //maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://nexus.eman.cz/repository/gradle-releases")
        jcenter()
        google()
        maven(url = "https://nexus.eman.cz/repository/gradle-snapshots")
    }

    dependencies {
        // https://github.com/gradle/kotlin-dsl/issues/1291
        classpath(GradlePlugins.androidGradle)

        // Kotlin Grade plugin
        classpath(GradlePlugins.kotlin)

        // Build Tool to generate Kotlin KDoc documentation
        classpath(GradlePlugins.dokka)

        classpath(GradlePlugins.mavenPublish)

        // EmanExtension Gradle Plugin
        classpath(GradlePlugins.emanExtension)

        // Spoon Gradle Plugin
        classpath(GradlePlugins.spoon)

        // SonarQube Gradle Plugin
        classpath(GradlePlugins.sonar)
    }
}

allprojects {
    repositories {
        //maven(url = "https://plugins.gradle.org/m2/")
        jcenter()
        google()
        maven(url = "https://nexus.eman.cz/repository/maven-releases/")
        maven(url = "https://nexus.eman.cz/repository/maven-snapshots/")

        // For Spoon snapshot, until 2.0.0 is released
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    }
}

apply(plugin = "cz.eman.extension")
//apply(plugin = "org.sonarqube")


/*sonarqube {
    properties {
        property("sonar.projectKey", Android.applicationId)
        property("sonar.projectVersion", getProperty("versionName") ?: Android.versionName)
        property("sonar.sourceEncoding", Android.encoding)
    }
}*/

tasks.create<Delete>("clean") {
    delete(rootProject.buildDir)
}
