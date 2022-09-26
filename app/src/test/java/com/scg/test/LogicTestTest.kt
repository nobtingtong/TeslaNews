package com.scg.test

import org.junit.Before
import org.junit.Test

class LogicTestTest {

    lateinit var logicTest: LogicTest

    @Before
    fun init() {
        logicTest = LogicTest()
    }

    @Test
    fun findSumOfLeftElements() {
        logicTest.findSumOfLeftElements(intArrayOf(1, 3, 5, 7, 9)).also { result ->
            assert(result == "middle index is 3")
        }
        logicTest.findSumOfLeftElements(intArrayOf(3, 6, 8, 1, 5, 10, 1, 7)).also { result ->
            assert(result == "middle index is 4")
        }
        logicTest.findSumOfLeftElements(intArrayOf(3, 5, 6)).also { result ->
            assert(result == "index not found")
        }
    }

    @Test
    fun isPalindrome() {
        logicTest.isPalindrome("aka").also { result ->
            assert(result == "aka is a palindrome")
        }
        logicTest.isPalindrome("level").also { result ->
            assert(result == "level is a palindrome")
        }
        logicTest.isPalindrome("Hello").also { result ->
            assert(result == "Hello isn’t a palindrome")
        }
    }
}