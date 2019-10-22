import org.gradle.api.JavaVersion

private object Versions {

    const val supportLib = "1.0.0"
    const val archLifecycle = "2.1.0"
    const val navigationComponent = "2.1.0"
    const val constraintLayout = "1.1.3"
    const val androidKtx = "1.0.1"
    const val androidxActivity = "1.0.0"
    const val room = "2.2.0-rc01"
    const val materialDesign = "1.1.0-alpha06"

    const val kotlin = "1.3.50"
    const val coroutinesCore = "1.3.2"
    const val coroutinesAndroid = "1.3.2"
    const val dokka = "0.9.17"

    const val gradle = "5.2.1"
    const val gradleBuildTools = "3.4.0"

    const val mavenPublish = "3.6.2"

    const val timber = "4.7.1"
    const val timberKtx = "0.1.0"
    const val koin = "2.0.0-beta-1"
    const val retrofit = "2.6.0"
    const val okHttp3LogIntercept = "3.9.1"

    const val picasso ="2.5.2"

    const val stetho = "1.5.0"
    const val leakCanary = "1.6.2"
    const val sentry = "1.7.5"
    const val debugDb = "1.0.6"
    const val flipper = "0.25.0"
    const val soloader = "0.5.1"

    const val junit = "4.12"
    const val kotlinTest = "3.3.2"
    const val coroutinesTest = "1.2.1"
    const val espresso = "3.0.2"
    const val spoon = "2.0.0-SNAPSHOT"
    const val uiAutomator = "2.2.0"
    const val supportTest = "1.1.0"
    const val mockk = "1.9"
    const val archCore = "2.0.0-rc01"
    val archUnit = "0.10.2"
    val slf4j = "1.7.26"

    // eMan Deps
    val kaal = "0.4.0"
}

/* =============================  ANDROID ============================= */

object Android {
    val applicationId = "cz.eman.kaalsample"

    val minSdk = 21
    val targetSdk = 28
    val compileSdk = 28

    val versionCode = 1
    val versionName = "1"
    val encoding = "UTF-8"

    val testInstrumentRunner = "android.support.test.runner.AndroidJUnitRunner"
    val sourceCompatibilityJava = JavaVersion.VERSION_1_8
    val targetCompatibilityJava = JavaVersion.VERSION_1_8
}

/* =============================  BUILD-PLUGINS ======================= */

object GradlePlugins {
    val encoding = "UTF-8"
    val gradle = Versions.gradle

    val androidGradle = "com.android.tools.build:gradle:${Versions.gradleBuildTools}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:${Versions.dokka}"
    val mavenPublish = "digital.wup:android-maven-publish:${Versions.mavenPublish}"
    val spoon = "com.jaredsburrows:gradle-spoon-plugin:${Versions.spoon}"
}

object Dependencies {
    /* =============================  KOTLIN ============================== */

    object Kotlin {
        val kotlinStbLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
        val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
    }

    /* =============================  LIBS ================================ */

    object Libs {
        val appCompat = "androidx.appcompat:appcompat:${Versions.supportLib}"
        val supportFragment = "androidx.fragment:fragment:${Versions.supportLib}"
        val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        val lifecycleExtension = "androidx.lifecycle:lifecycle-extensions:${Versions.archLifecycle}"
        val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:${Versions.archLifecycle}"
        val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.archLifecycle}"
        val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.navigationComponent}"
        val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationComponent}"
        val androidKtx = "androidx.core:core-ktx:${Versions.androidKtx}"
        val androidxActivity = "androidx.activity:activity-ktx:${Versions.androidxActivity}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
        const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
        val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"

        val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        val retrofitGsonConv = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        val okHttp3LogIntercept =
            "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp3LogIntercept}"

        val koinScope =
            "org.koin:koin-androidx-scope:${Versions.koin}" // Koin Android Scope feature
        val koinAndroid = "org.koin:koin-android:${Versions.koin}"
        val koinViewModel =
            "org.koin:koin-androidx-viewmodel:${Versions.koin}" // Koin Android ViewModel feature

        val timber = "com.jakewharton.timber:timber:${Versions.timber}"
        val timberKtx = "cz.eman.logger:timber-ktx:${Versions.timberKtx}"

        val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"

        // eMan Deps
        val kaalDomain = "cz.eman.kaal:kaal-domain:${Versions.kaal}"
        val kaalPresentation = "cz.eman.kaal:kaal-presentation:${Versions.kaal}"
        val kaalInfrastructure = "cz.eman.kaal:kaal-infrastructure:${Versions.kaal}"
        val kaalCore = "cz.eman.kaal:kaal-core:${Versions.kaal}"

        // Tools
        val sentry = "io.sentry:sentry-android:${Versions.sentry}"
        val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
        val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
        val noLeakCanary = "com.squareup.leakcanary:leakcanary-android-no-op:${Versions.leakCanary}"
        val leakCanaryFragment =
            "com.squareup.leakcanary:leakcanary-support-fragment:${Versions.leakCanary}"
        val debugDb = "com.amitshekhar.android:debug-db:${Versions.debugDb}"
        val flipper = "com.facebook.flipper:flipper:${Versions.flipper}"
        val soloader = "com.facebook.soloader:soloader:${Versions.soloader}"
    }

    /* =============================  TEST-LIBS =========================== */

    object TestLibs {
        val junit = "junit:junit:${Versions.junit}"
        val espressoCore = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
        val kotlinTest = "io.kotlintest:kotlintest-runner-junit5:${Versions.kotlinTest}"
        val kotlinCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
        const val mockkUnit = "io.mockk:mockk:${Versions.mockk}"
        const val mockkInstrument = "io.mockk:mockk-android:${Versions.mockk}"
        val spoonClient = "com.squareup.spoon:spoon-client:${Versions.spoon}"
        const val uiAutomator = "androidx.test.uiautomator:uiautomator:${Versions.uiAutomator}"
        const val testRunner = "androidx.test:runner:${Versions.supportTest}"
        const val testRules = "androidx.test:rules:${Versions.supportTest}"
        const val archCoreTest = "androidx.arch.core:core-testing:${Versions.archCore}"
        val archUnit = "com.tngtech.archunit:archunit-junit4:${Versions.archUnit}"
        val slf4j = "org.slf4j:slf4j-simple:${Versions.slf4j}"
    }
}