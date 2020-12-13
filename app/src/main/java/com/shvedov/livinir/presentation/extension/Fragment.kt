package com.shvedov.livinir.presentation.extension

import androidx.fragment.app.Fragment

fun <T> Fragment.requireActivityAs() = requireActivity() as T