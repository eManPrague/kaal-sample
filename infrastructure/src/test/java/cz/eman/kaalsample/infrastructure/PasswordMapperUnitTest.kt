package cz.eman.kaalsample.infrastructure

import cz.eman.kaalsample.domain.feature.usermanagement.model.PasswordText
import cz.eman.kaalsample.infrastructure.feature.usermanagement.db.entity.PasswordForbiddenCharsEntity
import cz.eman.kaalsample.infrastructure.feature.usermanagement.mapper.toDTO
import cz.eman.kaalsample.infrastructure.feature.usermanagement.mapper.toEntity
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class PasswordMapperUnitTest : StringSpec(){

    init {
        "From DTO To Entity" {
            PasswordForbiddenCharsEntity(0, "123,456abcD").toDTO().chars shouldBe "123,456abcD"
        }

        "From Entity to DTO" {
            PasswordText("123,456abcD").toEntity().chars shouldBe "123,456abcD"
            PasswordText("Anything").toEntity().id shouldBe 0
        }
    }
}