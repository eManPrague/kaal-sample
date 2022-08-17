package cz.eman.kaalsample.domain

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class TestTest : StringSpec() {

    init {

        "test the test"{
            2 + 2 shouldBe 3
        }
    }
}