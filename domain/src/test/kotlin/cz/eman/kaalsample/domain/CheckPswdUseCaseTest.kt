package cz.eman.kaalsample.domain

import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrngth
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPswdStrengthUseCase
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.GetPswdUnsupportedCharsUseCase
import io.kotlintest.TestCase
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.coEvery
import io.mockk.mockk

class CheckPswdUseCaseTest : StringSpec() {

    private val unsuportechCharsUC = mockk<GetPswdUnsupportedCharsUseCase>()

    private val checkPswd = CheckPswdStrengthUseCase(unsuportechCharsUC)

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)

        coEvery { unsuportechCharsUC() } returns " /"
    }

    init {

        "Password should not be short"{
            val result = checkPswd("abc")
            result shouldBe PswdStrngth.LOW
        }

        "Password should not contain forbidden chars"{
            val result = checkPswd(" ")
            result shouldBe PswdStrngth.UNSUPPORTED_CHAR
        }

    }

}