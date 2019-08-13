import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.gradle.LinkMapping
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.ByteArrayOutputStream

plugins {
    id("java-library")
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("maven")
}


dependencies {
//    implementation(project(":domain"))
    implementation(project(":shared"))

    // Kotlin
    implementation(Dependencies.Kotlin.kotlinStbLib)
    implementation(Dependencies.Kotlin.coroutinesCore)

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
    moduleName = "data"
    outputFormat = "html" // html, md, javadoc,
    outputDirectory = "$buildDir/dokka/html"
    //processConfigurations = ["compile"]
    //samples = ["samples/basic.kt", "samples/advanced.kt"]
    includes = listOf("../README.md")
    val file = File(project.projectDir,"src/main/kotlin")
    val relativePath = rootDir.toPath().relativize(file.toPath()).toString()
    linkMapping(delegateClosureOf<LinkMapping> {
        dir = file.path
        url = "https://github.com/eManPrague/kaal-sample/tree/master/$relativePath"
        suffix = "#L"
    })
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