package com.example.onlinestoretest.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Feedback(
    val count: Int,
    val rating: Double
) : Parcelable