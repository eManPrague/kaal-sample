package cz.eman.kaalsample.data

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.junit.ArchUnitRunner
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.runner.RunWith

@RunWith(ArchUnitRunner::class)
@AnalyzeClasses(packagesOf = [DataArchTest::class], importOptions = [ImportOption.DoNotIncludeTests::class, ImportOption.DoNotIncludeJars::class])
class DataArchTest {
    @ArchTest
    fun androidDependencyCheck(importedClasses: JavaClasses) {
        val dependencyCheck = noClasses()
                .that()
                .resideInAnyPackage("..data..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("android..", "androidx..")
        dependencyCheck.check(importedClasses)
    }

    @ArchTest
    fun appDependencyCheck(importedClasses: JavaClasses) {
        val dependencyCheck = noClasses()
                .that()
                .resideInAPackage("..data..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..app..")
        dependencyCheck.check(importedClasses)
    }

    @ArchTest
    fun infrastructureDependencyCheck(importedClasses: JavaClasses) {
        val dependencyCheck = noClasses()
                .that()
                .resideInAPackage("..data..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..infrastructure..")
        dependencyCheck.check(importedClasses)
    }

    @ArchTest
    fun repositoryResideInAPackageCheck(importedClasses: JavaClasses) {
        val resideInAPackage = classes()
                .that()
                .haveSimpleNameEndingWith("RepositoryImpl")
                .should()
                .resideInAPackage("..repository")
        resideInAPackage.check(importedClasses)
    }
}