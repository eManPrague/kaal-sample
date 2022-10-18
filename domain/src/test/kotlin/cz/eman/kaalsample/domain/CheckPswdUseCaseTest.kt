package cz.eman.kaalsample.domain

import cz.eman.kaalsample.domain.feature.usermanagement.model.EncriptedPswd
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength
import cz.eman.kaalsample.domain.feature.usermanagement.repository.PswdStrengthRepository
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPswdStrengthUseCase
import io.kotlintest.TestCase
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk

class CheckPswdUseCaseTest : StringSpec() {

    private val pswdRepository = mockk<PswdStrengthRepository>()

    private val checkPswd = CheckPswdStrengthUseCase(
        repository = pswdRepository
    )

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)

        coEvery {
            pswdRepository.getInvalidCharsInPswd()
        } returns "{}"

        coEvery {
            pswdRepository.getMinPswdLength()
        } returns 7
    }

    init {

        "test the test"{
            2 + 2 shouldBe 4
        }

        "emplty pasword should be invalid"{
            val result = checkPswd(EncriptedPswd(""))
            result.shouldBe(PswdStrength.INVALID)
        }

    }
}