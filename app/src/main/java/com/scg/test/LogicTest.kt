package com.scg.test

class LogicTest {
    fun findSumOfLeftElements(arr: IntArray): String {
        var leftIndex = 0
        var rightIndex = arr.size - 1
        var leftSum = arr[leftIndex]
        var rightSum = arr[rightIndex]
        while (leftIndex < rightIndex) {
            if (leftSum == rightSum && leftIndex + 2 == rightIndex) {
                return "middle index is ${leftIndex + 1}"
            }
            if (leftSum < rightSum) {
                leftSum += arr[++leftIndex]
            } else {
                rightSum += arr[--rightIndex]
            }
        }
        return "index not found"
    }

    fun isPalindrome(word: String): String {
        if (word.lowercase() == reverseString(word).lowercase()) {
            return "$word is a palindrome"
        }
        return "$word isn’t a palindrome"
    }

    private fun reverseString(word: String): String {
        var reverse = ""
        for (i in word.length - 1 downTo 0) {
            reverse += word[i]
        }
        return reverse
    }
}
