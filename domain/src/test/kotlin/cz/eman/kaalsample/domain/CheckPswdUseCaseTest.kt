package cz.eman.kaalsample.domain

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.domain.feature.usermanagement.model.EncriptedPswd
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength
import cz.eman.kaalsample.domain.feature.usermanagement.repository.PswdStrengthRepository
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPswdStrengthUseCase
import io.kotlintest.TestCase
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.coEvery
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
        } returns Result.success("{}")

        coEvery {
            pswdRepository.getMinPswdLength()
        } returns Result.Success(6)
    }

    init {

        "test the test"{
            2 + 2 shouldBe 4
        }

        "empty password should be invalid"{
            val result = checkPswd(EncriptedPswd(""))
            result.shouldBe(Result.Success(PswdStrength.INVALID))
        }

    }
}