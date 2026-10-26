package com.example.lab5_baitap

import java.io.Serializable

data class User(
    val fullName: String,
    val email: String,
    val phone: String,
    val username: String,
    val password: String
) : Serializable