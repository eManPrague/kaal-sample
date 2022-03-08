import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.gradle.LinkMapping

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("org.jetbrains.dokka")
}

android {
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = Android.applicationId

        minSdk = Android.minSdk
        targetSdk = Android.targetSdk

        versionCode = getGitCommits()
        versionName = findPropertyOrNull("versionName") ?: Android.versionName

        testInstrumentationRunner = Android.testInstrumentRunner

        packagingOptions {
            resources.excludes.addAll(
                listOf(
                    "META-INF/domain.kotlin_module",
                    "META-INF/data.kotlin_module",
                    "META-INF/infrastructure.kotlin_module",
                    "META-INF/LICENSE.md",
                    "META-INF/LICENSE-notice.md"
                )
            )
        }

    }

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("keystore/debug.jks")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"

        }

        create("uat") {
            initWith(signingConfigs.getByName("debug"))
        }

        create("release") {
            storeFile = rootProject.file("keystore/release.jks")
            storePassword = "${getPropertyOrDefaultValue("storePassword", "")}"
            keyAlias = "androiddebugkey" // todo key alias
            keyPassword = "${getPropertyOrDefaultValue("keyPassword", "")}"
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true

            applicationIdSuffix = ".dev"
            buildConfigField(
                "String",
                "SENTRY_DSN",
                "\"${getPropertyOrDefaultValue("sentryDsn", "")}\""
            )

            signingConfig = signingConfigs.getByName("debug")
        }

        create("uat") {
            isMinifyEnabled = false
            isDebuggable = true

            applicationIdSuffix = ".uat"

            buildConfigField("String", "SENTRY_DSN", "\"${findPropertyOrNull("sentryDsn")}\"")

            signingConfig = signingConfigs.getByName("uat")
        }

        getByName("release") {
            isMinifyEnabled = true

            buildConfigField("String", "SENTRY_DSN", "\"${findPropertyOrNull("sentryDsn")}\"")

            signingConfig = signingConfigs.getByName("release")
        }
    }

    testBuildType = "debug"

    flavorDimensions.add("api")

    productFlavors {
        create("apiDev") {
            dimension = "api"
        }

        create("apiProd") {
            dimension = "api"
        }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
        getByName("debug").java.srcDirs("src/debug/kotlin")
        getByName("uat").java.srcDirs("src/uat/kotlin")
        getByName("release").java.srcDirs("src/release/kotlin")
        getByName("apiDev").java.srcDirs("src/apiDev/kotlin")
        getByName("apiProd").java.srcDirs("src/apiProd/kotlin")
    }

    compileOptions {
        sourceCompatibility = Android.sourceCompatibilityJava
        targetCompatibility = Android.targetCompatibilityJava
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    lint {
        lintConfig = File("$rootDir/lint.xml")
    }
}

dependencies {
    implementation(project(":infrastructure"))
    implementation(project(":domain"))
    implementation(project(":data"))

    // Kotlin
    implementation(Dependencies.Kotlin.kotlinStbLib)
    implementation(Dependencies.Kotlin.coroutinesCore)
    implementation(Dependencies.Kotlin.coroutinesAndroid)

    // Google Android Libraries
    implementation(Dependencies.Libs.appCompat)
    implementation(Dependencies.Libs.androidxActivity)
    implementation(Dependencies.Libs.constraintLayout)
    implementation(Dependencies.Libs.androidKtx)
    implementation(Dependencies.Libs.navigationFragment)
    implementation(Dependencies.Libs.navigationUi)
    implementation(Dependencies.Libs.lifecycleRuntime)
    implementation(Dependencies.Libs.materialDesign)

    implementation(Dependencies.Libs.picasso)

    // eMan
    implementation(Dependencies.Libs.kaalPresentation)
    implementation(Dependencies.Libs.timberKtx)

    // Third Party - others
    implementation(Dependencies.Libs.koinAndroid)
    implementation(Dependencies.Libs.koinViewModel)
    implementation(Dependencies.Libs.koinScope)
    implementation(Dependencies.Libs.timber)
    implementation(Dependencies.Libs.sentry)
    implementation("androidx.appcompat:appcompat:1.4.1")


    // Tests
    testImplementation(Dependencies.TestLibs.junit)
    testImplementation(Dependencies.TestLibs.archCoreTest)
    testImplementation(Dependencies.TestLibs.kotlinCoroutinesTest)
    testImplementation(Dependencies.TestLibs.kotlinTest)
    testImplementation(Dependencies.TestLibs.mockkUnit)

    androidTestImplementation(Dependencies.TestLibs.mockkInstrument)
    androidTestImplementation(Dependencies.TestLibs.uiAutomator)
    androidTestImplementation(Dependencies.TestLibs.testRules)
    androidTestImplementation(Dependencies.TestLibs.testRunner)

    androidTestImplementation(Dependencies.TestLibs.espressoCore) {
        exclude(module = "support-annotations")
    }

    // Tools
    implementation(Dependencies.Libs.stetho)
    debugImplementation(Dependencies.Libs.leakCanary)
    debugImplementation(Dependencies.Libs.leakCanaryFragment)
    "uatImplementation"(Dependencies.Libs.noLeakCanary)
    releaseImplementation(Dependencies.Libs.noLeakCanary)
}

val dokka by tasks.getting(DokkaTask::class) {
    moduleName = "app"
    outputFormat = "html" // html, md, javadoc,
    outputDirectory = "$buildDir/dokka/html"
    includes = listOf("../README.md")
    val file = File(project.projectDir, "src/main/kotlin")
    val relativePath = rootDir.toPath().relativize(file.toPath()).toString()
    linkMapping(delegateClosureOf<LinkMapping> {
        dir = file.path
        url = "https://github.com/eManPrague/kaal-sample/tree/master/$relativePath"
        suffix = "#L"
    })
    sourceDirs = files("src/main/kotlin")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
