package com.example.crossword_designer.api


data class Meta(
    val ants: List<Any>,
    val id: String,
    val offensive: Boolean,
    val section: String,
    val src: String,
    val stems: List<String>,
    val syns: List<List<String>>,
    val target: Target,
    val uuid: String
)