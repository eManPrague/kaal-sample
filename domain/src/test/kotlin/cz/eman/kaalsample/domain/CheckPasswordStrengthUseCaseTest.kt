package cz.eman.kaalsample.domain

import cz.eman.kaalsample.domain.feature.usermanagement.model.PasswordStrength
import cz.eman.kaalsample.domain.feature.usermanagement.usecase.CheckPasswordStrengthUseCase
import io.kotlintest.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CheckPasswordStrengthUseCaseTest {

    @Test
    fun `password strength should be invalid`() {
        val checkPasswordStrength = CheckPasswordStrengthUseCase()
        runBlocking {
            listOf(
                " ",
                ",",
                "/",
                "kjsdf,ksjdf",
                "ijse kbse"
            ).forEach {
                val result = checkPasswordStrength(it)
                result shouldBe PasswordStrength.Invalid
            }
        }
    }

    @Test
    fun `password strength should be empty`() {
        val checkPasswordStrength = CheckPasswordStrengthUseCase()
        runBlocking {
            listOf(
                ""
            ).forEach {
                val result = checkPasswordStrength(it)
                result shouldBe PasswordStrength.Empty
            }
        }
    }

    @Test
    fun `password strength should be weak`() {
        val checkPasswordStrength = CheckPasswordStrengthUseCase()
        runBlocking {
            listOf(
                "kjh",
                "Khjf",
                "IUyH34"
            ).forEach {
                val result = checkPasswordStrength(it)
                result shouldBe PasswordStrength.Weak
            }
        }
    }

    @Test
    fun `password strength should be medium`() {
        val checkPasswordStrength = CheckPasswordStrengthUseCase()
        runBlocking {
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
    }

    @Test
    fun `password strength should be strong`() {
        val checkPasswordStrength = CheckPasswordStrengthUseCase()
        runBlocking {
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