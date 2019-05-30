import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.ByteArrayOutputStream

plugins {
    id("java-library")
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("maven")
    id("cz.eman.extension.java")
}

dependencies {
    // Kotlin
    implementation(Dependencies.Kotlin.kotlinStbLib)
    implementation(Dependencies.Kotlin.coroutinesCore)

    // eMan
    api(Dependencies.Libs.kaalDomain)

    // Tests
    testImplementation(Dependencies.TestLibs.junit)
    testImplementation(Dependencies.TestLibs.kotlinTest)
    testImplementation(Dependencies.TestLibs.mockkUnit)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val dokka by tasks.getting(DokkaTask::class) {
    moduleName = "domain"
    outputFormat = "html" // html, md, javadoc,
    outputDirectory = "$buildDir/dokka/html"
/*    processConfigurations = ["compile"]
    includes = ["../CHANGELOG.md"]
    //samples = ["samples/basic.kt", "samples/advanced.kt"]
    linkMapping {
        dir = "src/main/kotlin"
        url = "https://gitlab.eman.cz/TBD/TBD/TBD/tree/dev/TBD/src/main/kotlin"
        suffix = "#L"
    }*/
    sourceDirs = files("src/main/kotlin")
}

tasks.create<Jar>("sourcesJar") {
    from(files("src/main/kotlin"))
    archiveClassifier.set("sources")
}

tasks.create<Jar>("dokkaHtmlJar") {
    archiveClassifier.set("kdoc-html")
    from("$buildDir/dokka/html")
    dependsOn(dokka)
}