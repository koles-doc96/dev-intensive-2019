package ru.skillbranch.devintensive.utils

import java.util.*

object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>?
        val firstName: String?
        val lastName: String?
        when (fullName.isNullOrBlank()) {
            false -> {
                parts = fullName.trim().split(" ")
                firstName = parts.getOrNull(0)
                lastName = parts.getOrNull(1)
            }
            else -> {
                firstName = null
                lastName = null
            }
        }

        return firstName to lastName
    }

    fun plural(value: Int, declare: WordDeclension): String {
        val preLastDigit: Int = value % 100 / 10

        return if (preLastDigit == 1) {
            declare.getWords()[2]
        } else when (value % 10) {
            1 -> declare.getWords()[0]
            2, 3, 4 -> declare.getWords()[1]
            else -> declare.getWords()[2]
        }

    }

    val map: Map<String, String> = mapOf<String, String>(
        "а" to "a",

        "б" to "b",

        "в" to "v",

        "г" to "g",

        "д" to "d",

        "е" to "e",

        "ё" to "e",

        "ж" to "zh",

        "з" to "z",

        "и" to "i",

        "й" to "i",

        "к" to "k",

        "л" to "l",

        "м" to "m",

        "н" to "n",

        "о" to "o",

        "п" to "p",

        "р" to "r",

        "с" to "s",

        "т" to "t",

        "у" to "u",

        "ф" to "f",

        "х" to "h",

        "ц" to "c",

        "ч" to "ch",

        "ш" to "sh",

        "щ" to "sh'",

        "ъ" to "",

        "ы" to "i",

        "ь" to "",

        "э" to "e",

        "ю" to "yu",

        "я" to "ya"
    )


    fun transliteration(payload: String?, divider: String = " "): String {
        var result = ""
        if (!payload.isNullOrBlank() && !divider.isNullOrEmpty()) {
            for (each in payload) {
                result += when (each) {
                    ' ' -> divider
                    else -> if (each.isUpperCase())
                        map.getOrElse(each.toString().toLowerCase(Locale.ROOT), { each.toString() })
                            .capitalize()
                    else map.getOrElse(
                        each.toString().toLowerCase(Locale.ROOT),
                        { each.toString() })
                }
            }
        }
        return result.trim()
    }

    fun toInitials(firstName: String?, lastName: String?): String? {

        return when {
            !firstName.isNullOrBlank() && !lastName.isNullOrBlank() ->
                "${firstName[0].toUpperCase()}${lastName[0].toUpperCase()}"
            !firstName.isNullOrBlank() || !lastName.isNullOrBlank() ->
                "${if (!firstName.isNullOrBlank()) firstName[0].toUpperCase() else lastName?.get(0)
                    ?.toUpperCase()}"
            else -> null
        }
    }

    enum class WordDeclension() {
        SECOND {
            override fun getWords(): List<String> {
                return listOf<String>("секунду", "сеунд", "секунды")
            }
        },
        MINUTE {
            override fun getWords(): List<String> {
                return listOf<String>("минуту", "минуты", "минут")
            }
        },
        HOURS {
            override fun getWords(): List<String> {
                return listOf<String>("час", "часа", "часов")
            }
        },
        DAY {
            override fun getWords(): List<String> {
                return listOf<String>("день", "дня", "дней")
            }
        };

        abstract fun getWords(): List<String>
    }

}