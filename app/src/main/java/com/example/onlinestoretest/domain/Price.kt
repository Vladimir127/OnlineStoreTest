package com.example.onlinestoretest.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Price(
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String
) : Parcelable