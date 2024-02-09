package com.example.onlinestoretest.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Info(
    val title: String,
    val value: String
) : Parcelable