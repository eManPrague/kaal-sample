package cz.eman.kaalsample.domain

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.junit.ArchUnitRunner
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.runner.RunWith


@RunWith(ArchUnitRunner::class)
@AnalyzeClasses(packagesOf = [DomainArchTest::class], importOptions = [ImportOption.DoNotIncludeTests::class, ImportOption.DoNotIncludeJars::class])
class DomainArchTest {
    @ArchTest
    fun useCaseNameCheck(importedClasses: JavaClasses) {
        val nameCheck = classes()
                .that()
                .resideInAPackage("..usecase")
                .should()
                .haveSimpleNameEndingWith("UseCase")
                .orShould()
                .haveSimpleNameEndingWith("Params")
        nameCheck.check(importedClasses)
    }

    @ArchTest
    fun useCasePackageCheck(importedClasses: JavaClasses) {
        val packageCheck = classes()
                .that()
                .haveSimpleNameEndingWith("UseCase")
                .should()
                .resideInAPackage("..usecase")
        packageCheck.check(importedClasses)
    }

    @ArchTest
    fun repositoryResideInAPackageCheck(importedClasses: JavaClasses) {
        val resideInAPackage = classes()
                .that()
                .haveSimpleNameEndingWith("Repository")
                .should()
                .resideInAPackage("..repository")
        resideInAPackage.check(importedClasses)
    }

    @ArchTest
    fun dataSourceResideInAPackageCheck(importedClasses: JavaClasses) {
        val resideInAPackage = classes()
                .that()
                .haveSimpleNameEndingWith("DataSource")
                .should()
                .resideInAPackage("..source")
        resideInAPackage.check(importedClasses)
    }

    @ArchTest
    fun androidDependencyCheck(importedClasses: JavaClasses) {
        val dependencyCheck = noClasses()
                .that()
                .resideInAnyPackage("..domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("android..", "androidx..")
        dependencyCheck.check(importedClasses)
    }

    @ArchTest
    fun dataDependencyCheck(importedClasses: JavaClasses) {
        val dependencyCheck = noClasses()
                .that()
                .resideInAPackage("..domain..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..data..")
        dependencyCheck.check(importedClasses)
    }

    @ArchTest
    fun appDependencyCheck(importedClasses: JavaClasses) {
        val dependencyCheck = noClasses()
                .that()
                .resideInAPackage("..domain..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..app..")
        dependencyCheck.check(importedClasses)
    }

    @ArchTest
    fun infrastructureDependencyCheck(importedClasses: JavaClasses) {
        val dependencyCheck = noClasses()
                .that()
                .resideInAPackage("..domain..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..infrastructure..")
        dependencyCheck.check(importedClasses)
    }
}