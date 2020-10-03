package com.example.crossword_designer.api

data class WebsterItem(
    val def: List<Def>,
    val fl: String,
    val hwi: Hwi,
    val meta: Meta,
    val shortdef: List<String>
)