import org.jetbrains.dokka.gradle.DokkaTask
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.api.BaseVariantOutput
import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import org.jetbrains.dokka.gradle.LinkMapping

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("org.jetbrains.dokka")
    id("com.jaredsburrows.spoon")
}

android {
    compileSdkVersion(Android.compileSdk)

    defaultConfig {
        applicationId = Android.applicationId

        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)

        versionCode = getGitCommits()
        versionName = findPropertyOrNull( "versionName") ?: Android.versionName

        testInstrumentationRunner = Android.testInstrumentRunner

        packagingOptions {
            exclude("META-INF/domain.kotlin_module")
            exclude("META-INF/data.kotlin_module")
            exclude("META-INF/infrastructure.kotlin_module")
            exclude("META-INF/LICENSE.md")
            exclude("META-INF/LICENSE-notice.md")
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

            // Icon - based on branch name
            //val tmpBranchName = getPropertyOrDefaultValue('branchName', gitBranchName)
            //createIcons(tmpBranchName, "#ffffff", getBackgroundColor(tmpBranchName), "debug")
        }

        create("uat") {
            isMinifyEnabled = false
            isDebuggable = true

            applicationIdSuffix = ".uat"

            buildConfigField("String", "SENTRY_DSN", "\"${findPropertyOrNull("sentryDsn")}\"")

            signingConfig = signingConfigs.getByName("uat")

            /*createIcons(
                getPropertyOrDefaultValue("branchName", gitBranchName),
                "#ffffff",
                "#de3737",
                "uat"
            )*/
        }

        getByName("release") {
            isMinifyEnabled = true

            buildConfigField("String", "SENTRY_DSN", "\"${findPropertyOrNull("sentryDsn")}\"")

            signingConfig = signingConfigs.getByName("release")
        }
    }

    testBuildType = "debug"

    /*buildTypes {
        debug {
            applicationIdSuffix ".dev"

            debuggable true

            buildConfigField "String", "SENTRY_DSN", "\"${getPropertyOrDefaultValue("sentryDsn", "")}\""

            signingConfig signingConfigs.debug

            // Icon - based on branch name
            String tmpBranchName = getPropertyOrDefaultValue('branchName', gitBranchName)
            createIcons(tmpBranchName, "#ffffff", getBackgroundColor(tmpBranchName), "debug")
        }

        uat {
            applicationIdSuffix ".uat"

            debuggable true

            buildConfigField "String", "SENTRY_DSN", "\"${getPropertyValue("sentryDsn")}\""

            signingConfig signingConfigs.uat

            createIcons(getPropertyOrDefaultValue('branchName', gitBranchName), "#ffffff", "#de3737", "uat")
        }

        release {
            debuggable false

            buildConfigField "String", "SENTRY_DSN", "\"${getPropertyValue("sentryDsn")}\""

            signingConfig signingConfigs.release
        }
    }*/

    flavorDimensions("api")

    productFlavors {
        create("apiDev") {
            setDimension("api")
        }

        create("apiProd") {
            setDimension("api")
        }
    }

    applicationVariants.all(object : Action<ApplicationVariant> {
        override fun execute(variant: ApplicationVariant) {
            variant.outputs.all(object : Action<BaseVariantOutput> {
                override fun execute(output: BaseVariantOutput) {

                    val outputImpl = output as BaseVariantOutputImpl
                    outputImpl.outputFileName = "${rootProject.name}.apk"
                }
            })
        }
    })
    /*applicationVariants.all { variant ->
        variant.outputs.all {
            outputFileName = "${rootProject.name}.apk"
        }
    }*/

    /*dataBinding {
        enabled = true
    }*/

    /*testOptions {
        unitTests.apply {
            isReturnDefaultValues = true
            all(KotlinClosure1<Any, Test>({
                (this as Test).also {
                    maxParallelForks = Runtime.getRuntime().availableProcessors()
                }, this))
            }
        }
    }*/

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
        setTargetCompatibility(Android.targetCompatibilityJava)
    }

    lintOptions {
        setLintConfig(File("$rootDir/lint.xml"))
    }

}

/*configurations.all(object : Action<Configuration> {
    override fun execute(config: Configuration) {
        if (config.name.contains("UnitTest") || config.name.contains("AndroidTest")) {
            config.resolutionStrategy.all({
                execute()
            })
        }
    }
})
configurations.all { config ->
    if (config.name.contains("UnitTest") || config.name.contains("AndroidTest")) {
        config.resolutionStrategy.eachDependency { details ->
            if (details.requested.group == 'com.squareup.leakcanary' && details.requested.name == 'leakcanary-android') {
                details.useTarget(group: details.requested.group, name: 'leakcanary-android-no-op', version: details.requested.version)
            }
        }
    }
}*/

spoon {
    debug = true
    grantAll = true
    failIfNoDeviceConnected = true
    ignoreFailures = true
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


    // Tests
    testImplementation(Dependencies.TestLibs.junit)
    testImplementation(Dependencies.TestLibs.archCoreTest)
    testImplementation(Dependencies.TestLibs.kotlinCoroutinesTest)
    testImplementation(Dependencies.TestLibs.kotlinTest)
    testImplementation(Dependencies.TestLibs.mockkUnit)

    androidTestImplementation(Dependencies.TestLibs.mockkInstrument)
    androidTestImplementation(Dependencies.TestLibs.spoonClient)
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
}

