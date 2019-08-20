import org.jetbrains.dokka.gradle.DokkaTask
import java.io.ByteArrayOutputStream

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-kapt")
    id("org.jetbrains.dokka")
    id("digital.wup.android-maven-publish")
}

android {
    compileSdkVersion(Android.compileSdk)

    defaultConfig {
        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)

        versionCode = Android.versionCode
        versionName = "${project.version}"

        testInstrumentationRunner = Android.testInstrumentRunner
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }

        create("uat") {
            isMinifyEnabled = false
        }

        getByName("release") {
            isMinifyEnabled = false
        }
    }

    /*flavorDimensions("api")

    productFlavors {
        create("apiDev") {
            setDimension("api")
        }

        create("apiProd") {
            setDimension("api")
        }
    }*/

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
    }

    compileOptions {
        sourceCompatibility = Android.sourceCompatibilityJava
        setTargetCompatibility(Android.targetCompatibilityJava)

    }

    lintOptions {
        setLintConfig(File("lint.xml"))
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Dependencies.Kotlin.kotlinStbLib)
    implementation(Dependencies.Kotlin.coroutinesCore)
    implementation(Dependencies.Kotlin.coroutinesAndroid)

    kapt(Dependencies.Libs.roomCompiler)
    implementation(Dependencies.Libs.roomRuntime)
    implementation(Dependencies.Libs.roomKtx)
    implementation(Dependencies.Libs.retrofit)
    implementation(Dependencies.Libs.okHttp3LogIntercept)
    implementation(Dependencies.Libs.retrofitGsonConv)
    implementation(Dependencies.Libs.koinAndroid)
    implementation(Dependencies.Libs.timberKtx)

    implementation(Dependencies.Libs.picasso)

    // Test
    testImplementation(Dependencies.TestLibs.junit)
    testImplementation(Dependencies.TestLibs.kotlinTest)
    testImplementation(Dependencies.TestLibs.mockkUnit)
    testImplementation(Dependencies.TestLibs.mockkInstrument)
    testImplementation(Dependencies.TestLibs.testRunner)
    testImplementation(Dependencies.TestLibs.archCoreTest)
    testImplementation(Dependencies.TestLibs.archUnit)
    testImplementation(Dependencies.TestLibs.slf4j)
}

val dokka by tasks.getting(DokkaTask::class) {
    moduleName = "infrastructure"
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

tasks {
    val androidSourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(android.sourceSets["main"].java.srcDirs)
    }

    val androidDokkaHtmlJar by creating(Jar::class) {
        archiveClassifier.set("kdoc-html")
        from("$buildDir/dokka/html")
        dependsOn(dokka)
    }

    artifacts {
        add("archives", androidSourcesJar)
        add("archives", androidDokkaHtmlJar)
    }
}