package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(vaue: Int, units: TimeUnits): Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> vaue * SECOND
        TimeUnits.MINUTE -> vaue * MINUTE
        TimeUnits.HOUR -> vaue * HOUR
        TimeUnits.DAY -> vaue * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
//    val humanizeDate: String
//    val currentDate = date
//    val period = currentDate.time - this.time
//    val seconds = period / SECOND
//    humanizeDate = when {
//        seconds in 0..1 -> "только что"
//        seconds in 2..45 -> "несколько секунд назад"
//        seconds in 46..75 -> "минуту назад"
//        seconds in 76..45 * MINUTE / SECOND -> "${TimeUnits.MINUTE.plural(  period / MINUTE)} назад"
//        seconds in 46 * MINUTE / SECOND..75 * MINUTE / SECOND -> "час назад"
//        seconds in 75 * MINUTE / SECOND..22 * HOUR / SECOND -> "${TimeUnits.HOUR.plural(period / HOUR)} назад"
//        seconds in 22 * HOUR / SECOND..26 * HOUR / SECOND -> "день назад"
//        seconds in 26 * HOUR / SECOND..360 * DAY / SECOND -> "${TimeUnits.DAY.plural(period / DAY)} назад"
//        else -> "более года назад"
//    }

    val now = Date()
    val isFuture: Boolean

    // разница в мс
    var diff = now.time - this.time
    isFuture = diff <= 0
    diff = Math.abs(diff)


    if (isFuture) {
        // будущее
        return when (diff) {
            (0 * SECOND), (1 * SECOND) -> "через секунду"
            in (1 * SECOND)..(45 * SECOND) -> "через несколько секунд"
            in (45 * SECOND)..(75 * SECOND) -> "через минуту"
            in (75 * SECOND)..(45 * MINUTE) -> "через ${TimeUnits.MINUTE.plural(diff / MINUTE)}"
            in (45 * MINUTE)..(75 * MINUTE) -> "через час"
            in (75 * MINUTE)..(22 * HOUR) -> "через ${TimeUnits.HOUR.plural(diff / HOUR)}"
            in (22 * HOUR)..(26 * HOUR) -> "через день"
            in (26 * HOUR)..(360 * DAY) -> "через ${TimeUnits.DAY.plural(diff / DAY)}"
            else -> "более чем через год"
        }

    } else {
        return when (diff) {
            (0 * SECOND), (1 * SECOND) -> "только что"
            in (1 * SECOND)..(45 * SECOND) -> "несколько секунд назад"
            in (45 * SECOND)..(75 * SECOND) -> "минуту назад"
            in (75 * SECOND)..(45 * MINUTE) -> "${TimeUnits.MINUTE.plural(diff / MINUTE)} назад"
            in (45 * MINUTE)..(75 * MINUTE) -> "час назад"
            in (75 * MINUTE)..(22 * HOUR) -> "${TimeUnits.HOUR.plural(diff / HOUR)} назад"
            in (22 * HOUR)..(26 * HOUR) -> "день назад"
            in (26 * HOUR)..(360 * DAY) -> "${TimeUnits.DAY.plural(diff / DAY)} назад"
            else -> "более года назад"
        }
    }
}

enum class TimeUnits {
    SECOND {
        override fun plural(value: Long): String {
            return "$value ${Utils.plural(value.toInt(), Utils.WordDeclension.SECOND)}"
        }
    },
    MINUTE {
        override fun plural(value: Long): String {
            return "$value ${Utils.plural(value.toInt(), Utils.WordDeclension.MINUTE)}"
        }
    },
    HOUR {
        override fun plural(value: Long): String {
            return "$value ${Utils.plural(value.toInt(), Utils.WordDeclension.HOURS)}"
        }
    },
    DAY {
        override fun plural(value: Long): String {
            return "$value ${Utils.plural(value.toInt(), Utils.WordDeclension.DAY)}"
        }
    };

    abstract fun plural(value: Long): String
}