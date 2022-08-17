package cz.eman.kaalsample.domain

import cz.eman.kaalsample.domain.feature.usermanagement.repository.UserRepository
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPswdStrengthUseCase
import io.kotlintest.forAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row
import io.mockk.coEvery
import io.mockk.mockk

class CheckPswdStrengthUseCaseTest : StringSpec() {

    private val repo = mockk<UserRepository>()

    private val cpuc = CheckPswdStrengthUseCase(repo)

    init {

        "test the test"{
            2 + 2 shouldBe 3
        }

        "Strong password should be enough length"{
            coEvery { repo.getMinPswdLength() } returns 7

            val result = cpuc()
            result shouldBe false
        }

    }
}