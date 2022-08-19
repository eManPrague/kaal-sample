package cz.eman.kaalsample.app.presentation.feature.login

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.domain.feature.usermanagement.model.PasswordText
import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPasswordStrengthUseCase
import cz.eman.kaalsample.presentation.feature.login.mapper.toPasswordStrengthVo
import io.kotlintest.TestCase
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.coEvery
import io.mockk.mockk

class SecurityMapperUnitTest : StringSpec() {

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
        "Testing password strength accessibility after mapping" {
            CheckPasswordStrengthUseCase.PasswordState.EMPTY.toPasswordStrengthVo().isAcceptable shouldBe false
            CheckPasswordStrengthUseCase.PasswordState.FORBIDDEN_CHARS.toPasswordStrengthVo().isAcceptable shouldBe false
            CheckPasswordStrengthUseCase.PasswordState.SHORT.toPasswordStrengthVo().isAcceptable shouldBe false
            CheckPasswordStrengthUseCase.PasswordState.WEAK.toPasswordStrengthVo().isAcceptable shouldBe true
            CheckPasswordStrengthUseCase.PasswordState.NORMAL.toPasswordStrengthVo().isAcceptable shouldBe true
            CheckPasswordStrengthUseCase.PasswordState.STRONG.toPasswordStrengthVo().isAcceptable shouldBe true
        }

        "Testing password that does not meet requirements" {
            listOf(
                "",
                "    ",
                "a",
                "e1w",
                "as4fgh",
                ",sdgsdhsřčřčž565",
                "123456@World",
                ";hello",
                "SPACE HERE",
            ).forEach {
                val isAcceptable = checkPasswordStrength(it).getOrNull()!!.toPasswordStrengthVo().isAcceptable
                isAcceptable shouldBe false
            }
        }

        // Todo maybe add chinese and stuff
        "Testing passwords that meet requirements" {
            listOf(
                "ščřžýáí",
                "íáýž123456fg",
                "MYSECUREPASS",
                "MYS3CUR3PASS",
                "MyPassw",
                "mysimplepasswordlongerthansomeother",
                "MYPASSWORDONLYINCAPS",
                "ShortPassw0rd",
                "TotallyProtectedPasswordWithNumb3r",
            ).forEach {
                val isAcceptable = checkPasswordStrength(it).getOrNull()!!.toPasswordStrengthVo().isAcceptable
                isAcceptable shouldBe true
            }
        }

    }
}