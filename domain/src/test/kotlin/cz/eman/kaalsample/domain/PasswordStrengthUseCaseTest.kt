package cz.eman.kaalsample.domain

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.domain.feature.usermanagement.model.PasswordText
import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPasswordStrengthUseCase
import io.kotlintest.TestCase
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.coEvery
import io.mockk.mockk

class PasswordStrengthUseCaseTest : StringSpec() {

    private val securityRepository = mockk<SecurityRepository>()

    private val checkPasswordStrength = CheckPasswordStrengthUseCase(securityRepository)

    override fun beforeTest(testCase: TestCase) {

        coEvery {
            securityRepository.getForbiddenPasswordChars()
        } returns Result.success(PasswordText(" .,@;"))

        coEvery {
            securityRepository.getMinPasswordLength()
        } returns Result.success(7)


        super.beforeTest(testCase)
    }

    init {

        "Empty passwords" {
            listOf(
                "",
                "    "
            ).forEach {
                val strengthResult = checkPasswordStrength(it)
                strengthResult shouldBe Result.success(CheckPasswordStrengthUseCase.PasswordState.EMPTY)
            }
        }

        "Short passwords" {
            listOf(
                "a",
                "e1w",
                "as4fgh",
            ).forEach {
                val strengthResult = checkPasswordStrength(it)
                strengthResult shouldBe Result.success(CheckPasswordStrengthUseCase.PasswordState.SHORT)
            }
        }

        "Password contains special characters" {
            listOf(
                ",sdgsdhsřčřčž565",
                "123456@World",
                ";hello",
                "SPACE HERE",
            ).forEach {
                val strengthResult = checkPasswordStrength(it)
                strengthResult shouldBe Result.success(CheckPasswordStrengthUseCase.PasswordState.FORBIDDEN_CHARS)
            }
        }

        "password is weak" {
            listOf(
                "ščřžýáí",
                "íáýž123456fg",
                "MYSECUREPASS",
                "MYS3CUR3PASS"
            ).forEach {
                val strengthResult = checkPasswordStrength(it)
                strengthResult shouldBe Result.success(CheckPasswordStrengthUseCase.PasswordState.WEAK)
            }
        }

        "Password strength should be normal" {
            listOf(
                "MyPassw",
                "mysimplepasswordlongerthansomeother",
                "MYPASSWORDONLYINCAPS"
            ).forEach {
                val strengthResult = checkPasswordStrength(it)
                strengthResult shouldBe Result.success(CheckPasswordStrengthUseCase.PasswordState.NORMAL)
            }
        }

        "Password strength should be strong" {
            listOf(
                "ShortPassw0rd",
                "TotallyProtectedPasswordWithNumb3r",
            ).forEach {
                val strengthResult = checkPasswordStrength(it)
                strengthResult shouldBe Result.success(CheckPasswordStrengthUseCase.PasswordState.STRONG)
            }
        }
    }

}