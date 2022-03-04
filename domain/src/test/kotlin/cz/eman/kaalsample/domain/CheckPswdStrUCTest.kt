package cz.eman.kaalsample.domain

import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength
import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPsswdStrengthUseCase
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk

class CheckPswdStrUCTest : StringSpec() {

    private val repo = mockk<SecurityRepository>()

    private val uc = CheckPsswdStrengthUseCase(repo)

    init {

        "Test the test"{

            2 + 2 shouldBe 3
        }

        "US suhld reutn " {
            val result = uc("")
            result shouldBe PswdStrength.Invalid
        }

        "Password can not contian forbiden characters"{
            every { repo.getFch() } returns "ABC"
            val result = uc("JANOSIK")
            result shouldBe PswdStrength.ForbidenChar("A")
        }
    }
}