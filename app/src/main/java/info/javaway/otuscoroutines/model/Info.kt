package info.javaway.otuscoroutines.model

import kotlin.String

data class Info constructor(
    var count: Int,
    var pages: Int,
    var next: String,
    var prev: String
)