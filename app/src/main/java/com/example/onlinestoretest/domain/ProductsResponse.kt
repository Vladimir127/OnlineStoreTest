package com.example.onlinestoretest.domain

import com.google.gson.annotations.SerializedName

data class ProductsResponse(/*@SerializedName("items")*/ val items: List<Product>) {
}