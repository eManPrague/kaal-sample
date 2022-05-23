import org.gradle.api.JavaVersion

/* =============================  ANDROID ============================= */

object Android {
    val applicationId = "cz.eman.kaalsample"

    val minSdk = 21
    val targetSdk = 29
    val compileSdk = 31

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
        val appCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        val supportFragment = "androidx.fragment:fragment:${Versions.fragment}"
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

        val koinScope = "io.insert-koin:koin-androidx-scope:${Versions.koin}"
        val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"
        val koinViewModel = "io.insert-koin:koin-androidx-viewmodel:${Versions.koin}"

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
    }

    /* =============================  TEST-LIBS =========================== */

    object TestLibs {
        val junit = "junit:junit:${Versions.junit}"
        val espressoCore = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
        val kotlinTest = "io.kotlintest:kotlintest-runner-junit5:${Versions.kotlinTest}"
        val kotlinCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
        const val mockkUnit = "io.mockk:mockk:${Versions.mockk}"
        const val mockkInstrument = "io.mockk:mockk-android:${Versions.mockk}"
        const val uiAutomator = "androidx.test.uiautomator:uiautomator:${Versions.uiAutomator}"
        const val testRunner = "androidx.test:runner:${Versions.supportTest}"
        const val testRules = "androidx.test:rules:${Versions.supportTest}"
        const val archCoreTest = "androidx.arch.core:core-testing:${Versions.archCore}"
        val archUnit = "com.tngtech.archunit:archunit-junit4:${Versions.archUnit}"
        val slf4j = "org.slf4j:slf4j-simple:${Versions.slf4j}"
        val kotest = "io.kotest:kotest-runner-junit5:${Versions.kotest}"
    }
}
