package cz.eman.kaalsample.domain

import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.result.onSuccess
import cz.eman.kaalsample.domain.feature.usermanagement.model.PswdStrength
import cz.eman.kaalsample.domain.feature.usermanagement.repository.UserRepository
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPswdStrengthUseCase
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.PswdErrors
import io.kotest.common.runBlocking
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotlintest.TestCase
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.coEvery
import io.mockk.mockk

class CheckPswdStrengthUseCaseTest : StringSpec() {

    private val repo = mockk<UserRepository>()

    private val checkPswdStrength = CheckPswdStrengthUseCase(repo)

    override fun beforeTest(testCase: TestCase) {
        coEvery {
            repo.getMinPswLength()
        } returns 7

        coEvery {
            repo.getForbiddencharsInPswd()
        } returns "&"

        coEvery {
            repo.getSpecialCharsInPswd()
        } returns ("""^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?])[A-Za-z\d@!%*?]{7,}$""".toRegex()).toString()

        super.beforeTest(testCase)
    }

    init {
        //just for control that it's working
        "test the test"{
            2 + 2 shouldBe 3
        }

        "Password should not contain forbidden character" {
            val result = checkPswdStrength("abcdef&")
            result.shouldBeInstanceOf<Result.Success<PswdStrength>>()
            result.onSuccess { it.shouldBe(PswdStrength.INVALID) }
        }

        "Strong password should be long enough"{
            val result = checkPswdStrength("abcd")
            result.shouldBeInstanceOf<Result.Success<PswdStrength>>()
            result.onSuccess { it.shouldBe(PswdStrength.LOW) }
        }

        "Password should be at least seven characters" {
            forAll(
                row("", PswdErrors.PSWD_CAN_NOT_PROCESS),
                row("ab&1", PswdStrength.INVALID),
                row("abc", PswdStrength.LOW),
                row("1234567", PswdStrength.MEDIUM),
                row("aA1cdefgh?", PswdStrength.OK)
            ) { pswd, result ->
                runBlocking {
                    val res = checkPswdStrength(pswd)
                    res.shouldBeInstanceOf<Result<Any>>()
                    res.onSuccess { it.shouldBe(result) }
                }
            }
        }
    }
}