import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom
import java.io.ByteArrayOutputStream

fun Project.findPropertyOrNull(s: String) = findProperty(s) as String?

fun Project.getProperty(propertyName: String) = property(propertyName)

fun Project.getPropertyOrDefaultValue(propertyName: String, defaultValue: Any) = findProperty(propertyName) ?: defaultValue

/**
 * Gets number of commits in projects repository.
 * @return Number of commits in projects repository.
 */
fun Project.getGitCommits(vararg paths: String): Int {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine = if (paths.isNotEmpty()) {
            listOf("git", "rev-list", "--count", "HEAD", "--", paths.joinToString(separator = " "))
        } else {
            listOf("git", "rev-list", "--count", "HEAD")
        }
        standardOutput = stdout
    }
    return try {
        Integer.parseInt(stdout.toString().trim())
    } catch (ex: NumberFormatException) {
        0
    }
}

/**
 * Find out if build is tagged as snapshot or not.
 * @return true if build is tagged as snapshot.
 */
fun Project.isSnapshot(): Boolean {
    return if (project.hasProperty("version")) {
        project.property("version").toString().endsWith("-SNAPSHOT")
    } else {
        false
    }
}