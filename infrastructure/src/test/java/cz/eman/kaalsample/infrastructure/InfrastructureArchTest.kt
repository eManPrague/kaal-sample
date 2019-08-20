package cz.eman.kaalsample.infrastructure

import androidx.room.Dao
import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.junit.ArchUnitRunner
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.runner.RunWith


@RunWith(ArchUnitRunner::class)
@AnalyzeClasses(packagesOf = [InfrastructureArchTest::class], importOptions = [ImportOption.DoNotIncludeTests::class, ImportOption.DoNotIncludeJars::class])
class InfrastructureArchTest {
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
    fun entityNameCheck(importerClasses: JavaClasses) {
        val nameCheck = classes()
                .that()
                .resideInAPackage("..entity")
                .should()
                .haveSimpleNameEndingWith("Entity")
        nameCheck.check(importerClasses)
    }

    @ArchTest
    fun entitiesResideInAPackageCheck(importerClasses: JavaClasses) {
        val resideInAPackage = classes()
                .that()
                .haveSimpleNameEndingWith("Entity")
                .should()
                .resideInAPackage("..entity")
        resideInAPackage.check(importerClasses)
    }

    @ArchTest
    fun daoNameCheck(importerClasses: JavaClasses) {
        val nameCheck = classes()
                .that()
                .resideInAPackage("..dao")
                .and()
                .areInterfaces()
                .should()
                .haveSimpleNameEndingWith("Dao")
        nameCheck.check(importerClasses)
    }

    @ArchTest
    fun daoResideInAPackageCheck(importerClasses: JavaClasses) {
        val resideInAPackage = classes()
                .that()
                .haveNameMatching(".*Dao")
                .should()
                .resideInAPackage("..dao")
        resideInAPackage.check(importerClasses)
    }

    @ArchTest
    fun daoAnnotationCheck(importerClasses: JavaClasses) {
        val annotationCheck = classes()
                .that()
                .resideInAPackage("..dao")
                .and()
                .areInterfaces()
                .should()
                .beAnnotatedWith(Dao::class.java)
        annotationCheck.check(importerClasses)
    }

    @ArchTest
    fun appDependencyCheck(importedClasses: JavaClasses) {
        val dependencyCheck = noClasses()
                .that()
                .resideInAPackage("..infrastructure..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..app..")
        dependencyCheck.check(importedClasses)
    }
}