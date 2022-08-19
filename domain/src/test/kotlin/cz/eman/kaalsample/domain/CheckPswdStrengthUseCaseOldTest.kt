package cz.eman.kaalsample.domain

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPasswordStrengthUseCase
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.coEvery
import io.mockk.mockk

class CheckPswdStrengthUseCaseOldTest : StringSpec() {

    private val repo = mockk<SecurityRepository>()

    private val cpuc = CheckPasswordStrengthUseCase(repo)

    init {
        "test the test" {
            2 + 2 shouldBe 3
        }

        "Password test" {

        }

        "Strong password length test" {
            coEvery { repo.getMinPasswordLength() } returns Result.success(7)
            val result = cpuc("abcd")
            result shouldBe false

            /*
            forAll(
                row("123456789", true),
                row("123456", false),
                row("1234567", true)
            ) { pswd, result ->
                result shouldBe cpuc(pswd)
            }
            */
        }
    }
}