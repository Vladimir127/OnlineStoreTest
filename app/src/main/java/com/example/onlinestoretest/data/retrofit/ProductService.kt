package com.example.onlinestoretest.data.retrofit

import com.example.onlinestoretest.domain.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ProductService {
    @GET
    suspend fun getProducts(@Url anEmptyString: String): ProductsResponse
}