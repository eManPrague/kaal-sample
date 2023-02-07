package cz.eman.kaalsample.domain

import cz.eman.kaalsample.domain.feature.usermanagement.model.Password
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength
import cz.eman.kaalsample.domain.feature.usermanagement.repository.PasswordRepository
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPswdStrengthUseCase
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk

class PswdStrengthUseCaseTest : StringSpec() {

    private val pswdRepo = mockk<PasswordRepository>()

    private val checkPswd = CheckPswdStrengthUseCase(pswdRepo)

    init {

        "test the test" {
            1 + 1 shouldBe 2
        }

        "Password should newer been empty" {
            val result = checkPswd(Password(""))
            result shouldBe PswdStrength.EMPTY
        }

        "Medium strength password should be at least 6 characters" {
            val result = checkPswd(Password("123456"))
            result shouldBe PswdStrength.MEDIUM
        }

        "Password can not contain space char" {
            every { pswdRepo.getForbiddenChars() } returns arrayOf()
            val result = checkPswd(Password("abc dfg"))
            result shouldBe PswdStrength.INVALID
        }
    }
}