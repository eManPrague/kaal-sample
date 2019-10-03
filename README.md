# Kotlin App Template with Android Clean Architecture & AAC by eMan

* Version: 2.0.0
* AAC + Kotlin Coroutines, Channels, ...

## TODO
* Rename project
* If you edit name of module (app), you also need to edit it in .gitlab-ci.yml
* App icons put just to release folder. Debug are generated from release.

## Types of build

### Build type
* **Debug** - Proguard off / parameter `-Pprogurad=true` turn Proguard on
* **Release** - Proguard on

### App sign
* If parameters `KeystorePass` and `KeyPass` are provided, gradlew generate signed apk file.

### Flavours
## Gradle
### Dependencies and Versions

All versions of dependencies are defined inside of **/buildSrc/src/main/kotlin/Dependencies.kt** file 
```kotlin
private object Versions {

    val supportLib = "1.0.0"
    val archLifecycle = "2.1.0"
    val navigationComponent = "2.1.0"
    val constraintLayout = "1.1.2"
    val androidKtx = "1.0.1"
    const val archPersistence = "2.0.0"
    
    //....
}
```

Dependencies are grouped by theirs meaning such like `Android`, `Gradle plugins`, `Kotlin`, `Libs` and `Test Libs`

#### How to use dependencies inside of modules

To use dependencies defined inside of the dependencies.gradle you just need to add 
reference to group and dependency name inside of the dependencies block:

```kotlin
dependencies {
    implementation(project(":infrastructure"))
    implementation(project(":domain"))
    implementation(project(":data"))

    // Kotlin
    implementation(Dependencies.Kotlin.kotlinStbLib)
    implementation(Dependencies.Kotlin.coroutinesCore)

    // Google Android Libraries
    implementation(Dependencies.Libs.appCompat)
    implementation(Dependencies.Libs.constraintLayout)
}
```

### Define Gradle Plugins
Gradle plugins are defined in same way like dependencies -> inside of the `Dependencies.kt` file.

```kotlin
object GradlePlugins {
    val encoding = "UTF-8"
    val gradle = Versions.gradle

    val androidGradle = "com.android.tools.build:gradle:${Versions.gradleBuildTools}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:${Versions.dokka}"
    val mavenPublish = "digital.wup:android-maven-publish:${Versions.mavenPublish}"
    val spoon = "com.jaredsburrows:gradle-spoon-plugin:${Versions.spoon}"

    val emanExtension = "cz.eman.gradle:gradle-extension-plugin:${Versions.emanExtension}"
 }

```
Then in the top level ```build.gradle``` file you can use them like this:

```kotlin
buildscript {

    //...

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
    }
}

```