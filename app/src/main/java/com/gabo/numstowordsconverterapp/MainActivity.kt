package com.gabo.numstowordsconverterapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gabo.numstowordsconverterapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var numberInWords: String
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val output = binding.tvInput
        binding.btnClick.setOnClickListener {
            val input = binding.etInput.text.toString()
            when {
                input == "" -> { output.text = getString(R.string.errorMsgEmptyNumber) }
                input.toInt() <= 0 || input.toInt() > 1000 -> {
                    output.text = getString(R.string.errorMsgWrongNumber)
                }
                else -> {
                    when (input.length) {
                        1 -> { output.text = convertTwoDigitNumToWord(input, input[0], null) }
                        2 -> { output.text = convertTwoDigitNumToWord(input, input[0], input[1]) }
                        3 -> { output.text = convertThreeDigitNumToWord(input) }
                        4 -> { output.text = "ათასი" }
                        else -> { output.text = getString(R.string.errorMsgWrongNumber) }
                    }
                }
            }
        }
    }

    private fun convertTwoDigitNumToWord(input: String, input0: Char?, input1: Char?): String {
        if (input.toInt() <= 20) {
            numberInWords = upToTwenty[input.toInt() - 1]
        } else if (input.toInt() < 100) {
            when (input1) {
                '0' -> numberInWords = tens[(input0!! - 3).toString().toInt()]
                else -> {
                    val untilTen = upToTwenty[(input1.toString().toInt() - 1)]
                    val fromTenToTwenty = upToTwenty[(input1.toString().toInt() + 9)]
                    val startingNum = when (input0.toString().toInt()) {
                        2, 3 -> 0; 4, 5 -> 1; 6, 7 -> 2; else -> 3
                    }
                    when (input0.toString().toInt()) {
                        2, 4, 6, 8 -> { numberInWords = tensWithNum[startingNum] + untilTen }
                        3, 5, 7, 9 -> { numberInWords = tensWithNum[startingNum] + fromTenToTwenty }
                    }
                }
            }
        }
        return (numberInWords)
    }

    private fun convertThreeDigitNumToWord(input: String): String {
        val hundredsIndex = input[0].toString().toInt() - 1
        val lastTwoDigits: String
        if (input.toInt() < 1000) {
            when (input[1]) {
                '0' -> {
                    when (input[2]) {
                        '0' -> numberInWords = hundreds[hundredsIndex]
                        else -> {
                            lastTwoDigits =
                                convertTwoDigitNumToWord((input.drop(1)), input[1], input[2])
                            numberInWords = hundredsWithNum[hundredsIndex] + lastTwoDigits
                        }
                    }
                }
                else -> {
                    lastTwoDigits = convertTwoDigitNumToWord(input.drop(1), input[1], input[2])
                    numberInWords = hundredsWithNum[hundredsIndex] + lastTwoDigits
                }
            }
        }
        return numberInWords
    }

    private val upToTwenty = listOf(
        "ერთი", "ორი", "სამი", "ოთხი", "ხუთი", "ექვსი", "შვიდი", "რვა", "ცხრა",
        "ათი", "თერთმეტი", "თორმეტი", "ცამეტი", "თოთხმეტი", "თხუთმეტი",
        "თექვსმეტი", "ჩვიდმეტი", "თვრამეტი", "ცხრამტი", "ოცი"
    )
    private val tens = listOf(
        "ოცდაათი", "ორმოცი", "ორმოცდაათი", "სამოცი", "სამოცდაათი", "ოთხმოცი", "ოთხმოცდაათი", "ასი"
    )
    private val tensWithNum = listOf("ოცდა", "ორმოცდა", "სამოცდა", "ოთხმოცდა")
    private val hundreds = listOf(
        "ასი", "ორასი", "სამასი", "ოთხასი", "ხუთასი", "ექვსასი", "შვიდასი", "რვაასი", "ცხრაასი"
    )
    private val hundredsWithNum =
        listOf(
            "ას ", "ორას ", "სამას ", "ოთხას ", "ხუთას ", "ექვსას ", "შვიდას ", "რვაას ", "ცხრაას "
        )
}