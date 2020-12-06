package com.shvedov.livinir.utils.extension

import androidx.fragment.app.Fragment

fun <T> Fragment.requireActivityAs() = requireActivity() as T