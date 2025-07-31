package com.ranggacikal.assesmentgithubuser.feature.user.utils

fun String?.orDefault(default: String): String {
    return if (this.isNullOrBlank()) default else this
}