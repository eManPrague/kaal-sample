import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.gradle.LinkMapping
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-library")
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("maven-publish")
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
    testImplementation(Dependencies.TestLibs.archUnit)
    testImplementation(Dependencies.TestLibs.slf4j)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

val dokka by tasks.getting(DokkaTask::class) {
    moduleName = "domain"
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