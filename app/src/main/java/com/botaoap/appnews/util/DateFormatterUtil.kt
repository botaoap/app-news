package com.botaoap.appnews.util

import java.text.SimpleDateFormat
import java.util.Locale

fun dateFormatterUtil(dateToFormatter: String) : String? {
    // Input date string

    // Parse the input date string
    val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val date: java.util.Date? = inputFormatter.parse(dateToFormatter)

    // Format the date to the desired output format
    val outputFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val outputDateString = date?.let { outputFormatter.format(it) }

    // Print the result
    return outputDateString // Output: 06/02/2025

}