package cz.eman.kaalsample.domain

import cz.eman.kaal.domain.result.ErrorCode
import cz.eman.kaal.domain.result.ErrorResult
import cz.eman.kaal.domain.result.Result
import cz.eman.kaal.domain.result.onError
import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.PswdStrengthUseCase
import io.kotlintest.TestCase
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.coEvery
import io.mockk.mockk

class PswdStrengthUsecaseTest : StringSpec() {

    private val repo = mockk<SecurityRepository>()

    private val pswdStrength = PswdStrengthUseCase(repo)

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)

        coEvery { repo.getSepcialChars() } returns "$"
    }

    init {

        "7 char long pswd is enough"{
            val reusult = pswdStrength("abcdefgi")
            reusult.shouldBeInstanceOf<Result.Success<Boolean>>()
        }

        "empty password shoud be an error" {
            pswdStrength("").onError {
                it shouldBe ErrorResult(code = ErrorCode.UNDEFINED)
            }
        }
    }
}