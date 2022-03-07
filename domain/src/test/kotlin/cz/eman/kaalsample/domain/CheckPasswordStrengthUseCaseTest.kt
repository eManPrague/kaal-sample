package cz.eman.kaalsample.domain

import cz.eman.kaalsample.domain.feature.usermanagement.model.PasswordStrength
import cz.eman.kaalsample.domain.feature.usermanagement.repository.SecurityRepository
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPasswordStrengthUseCase
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CheckPasswordStrengthUseCaseTest : StringSpec() {

    private val securityRepository = mockk<SecurityRepository>()

    private val checkPasswordStrength = CheckPasswordStrengthUseCase(securityRepository)

    init {
        every { securityRepository.getForbiddenCharacters() } returns
                listOf('/', ',', ' ', '<', '>')
        every { securityRepository.getSuggestedCharacters() } returns
                listOf('!', '@', '#', '?', '(', ')', '[', ']', '{', '}', '"', '$', '%',
                    '&', '\'', '*', '+', '-', '.', ':', ';', '=', '\\', '^', '_', '`', '|', '~')

        "password strength should be invalid" {
            listOf(
                "bjkjh fhgd",
                "as,dasad",
                "fgawg/hzzdj",
                "kjsdf,ksjdf",
                "ijse>kbse"
            ).forEach {
                val result = checkPasswordStrength(it)
                result shouldBe PasswordStrength.Invalid
            }
        }

        "password strength should be empty" {
            listOf(
                ""
            ).forEach {
                val result = checkPasswordStrength(it)
                result shouldBe PasswordStrength.Empty
            }
        }

        "password strength should be weak" {
            listOf(
                "kjh",
                "Khjf",
                "IUyH34"
            ).forEach {
                val result = checkPasswordStrength(it)
                result shouldBe PasswordStrength.Weak
            }
        }

        "password strength should be medium" {
            listOf(
                "asdfghjk",
                "qwerTyUiop",
                "zxcvb76j5h",
                "hd4lFG6e"
            ).forEach {
                val result = checkPasswordStrength(it)
                result shouldBe PasswordStrength.Medium
            }
        }

        "password strength should be strong" {
            listOf(
                "ad@FkJ78",
                "DfgG!5jkjh",
                "assd56HG#67"
            ).forEach {
                val result = checkPasswordStrength(it)
                result shouldBe PasswordStrength.Strong
            }
        }

    }

}
//
//    @Test
//    fun `password strength should be invalid`() {
//        val checkPasswordStrength = CheckPasswordStrengthUseCase()
//        runBlocking {
//            listOf(
//                " ",
//                ",",
//                "/",
//                "kjsdf,ksjdf",
//                "ijse kbse"
//            ).forEach {
//                val result = checkPasswordStrength(it)
//                result shouldBe PasswordStrength.Invalid
//            }
//        }
//    }
//
//    @Test
//    fun `password strength should be empty`() {
//        val checkPasswordStrength = CheckPasswordStrengthUseCase()
//        runBlocking {
//            listOf(
//                ""
//            ).forEach {
//                val result = checkPasswordStrength(it)
//                result shouldBe PasswordStrength.Empty
//            }
//        }
//    }
//
//    @Test
//    fun `password strength should be weak`() {
//        val checkPasswordStrength = CheckPasswordStrengthUseCase()
//        runBlocking {
//            listOf(
//                "kjh",
//                "Khjf",
//                "IUyH34"
//            ).forEach {
//                val result = checkPasswordStrength(it)
//                result shouldBe PasswordStrength.Weak
//            }
//        }
//    }
//
//    @Test
//    fun `password strength should be medium`() {
//        val checkPasswordStrength = CheckPasswordStrengthUseCase()
//        runBlocking {
//            listOf(
//                "asdfghjk",
//                "qwerTyUiop",
//                "zxcvb76j5h",
//                "hd4lFG6e"
//            ).forEach {
//                val result = checkPasswordStrength(it)
//                result shouldBe PasswordStrength.Medium
//            }
//        }
//    }
//
//    @Test
//    fun `password strength should be strong`() {
//        val checkPasswordStrength = CheckPasswordStrengthUseCase()
//        runBlocking {
//            listOf(
//                "ad@FkJ78",
//                "DfgG!5jkjh",
//                "assd56HG#67"
//            ).forEach {
//                val result = checkPasswordStrength(it)
//                result shouldBe PasswordStrength.Strong
//            }
//        }
//    }