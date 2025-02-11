package com.botaoap.appnews.customview

data class DebtSupportModel(
    val title: String,
    val description: String,
    val progressPercentage: Int,
    val isOpen: Boolean,
    val sectionHide: SectionHideModel
)

data class SectionHideModel(
    val title: String,
    val description: String
)