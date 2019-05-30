package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * @author vsouhrada (vaclav.souhrada@eman.cz)
 */
open class NewVersionInfo : DefaultTask() {

    init {
        group = "cz.eman.core"
        description = "Task to send emails about a new Android Core Arch"
    }

    @InputDirectory
    var workingDir: File = project.projectDir

    @TaskAction
    fun run() {
        val sendEmailUrl = "https://raw.githubusercontent.com/mogaal/sendemail/master/sendEmail"
        val subject = "New Android Core Arch version ${project.version}"
        val message = "New Connect Core Arch v${project.version} was published."
        val mails = "vaclav.souhrada@eman.cz"
        val sender = "vaclav.souhrada@eman.cz"

        // https://github.com/phatblat/ShellExec/blob/master/build.gradle.kts
        val command = "curl $sendEmailUrl | perl - -t $mails -f $sender -u $subject -m $message -s 10.0.0.2 -o tls=no"

        val shellCommand = ShellCommand(baseDir = workingDir, command = command)
        shellCommand.start()

        // Close up all the streams as we are done using shell exec
        shellCommand.process.inputStream.close()
        shellCommand.process.errorStream.close()
    }
}