package ru.skillbranch.devintensive.extensions

fun String.truncate(value: Int = 16): String {
    val text: String = this.trim()

    return if (text.length > value) {
        text.substring(0, value).trim() + "..."
    } else text
}

fun String.stripHtml(): String {
    val text: String = this.trim()
    val patternSpace: Regex = "\\s+".toRegex()
    val patternTag: Regex = "<[^>]*>".toRegex()
    val patternOut: Regex = "[&'\"]".toRegex()
    return text.replace(patternTag,"").replace(patternOut, "").replace(patternSpace, " ")
}
