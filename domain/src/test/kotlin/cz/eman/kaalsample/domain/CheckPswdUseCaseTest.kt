package cz.eman.kaalsample.domain

import cz.eman.kaal.domain.result.Result
import cz.eman.kaalsample.domain.feature.usermanagement.model.EncriptedPswd
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrengthConfig
import cz.eman.kaalsample.domain.feature.usermanagement.repository.PswdStrengthRepository
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPswdStrengthUseCase
import io.kotest.common.runBlocking
import io.kotest.data.blocking.forAll
import io.kotest.data.row
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
            pswdRepository.getPswdStrengthConfig()
        } returns Result.success(PswdStrengthConfig(invalidChars = """ \"#$'/[]^{|}""", pswdLength = 8))
    }

    init {

        "test the test"{
            2 + 2 shouldBe 4
        }

        "No invalid characters allowed" {
            val result = checkPswd(EncriptedPswd("acsdas###"))
            result.shouldBe(Result.success(PswdStrength.INVALID))
        }

        "No short passwords" {
            forAll(
                row("", Result.success(PswdStrength.SHORT)),
                row("ab1", Result.success(PswdStrength.SHORT)),
                row("abcdefgh", Result.success(PswdStrength.WEAK)),
                row("abcdefgh1", Result.success(PswdStrength.MEDIUM)),
                row("aA1cdefgh?adasdac", Result.success(PswdStrength.STRONG))
            ) { pswd, result ->
                runBlocking {
                    val res = checkPswd(EncriptedPswd(pswd))
                    res.shouldBe(result)
                }
            }
        }
    }
}