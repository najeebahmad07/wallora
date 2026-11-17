package com.example.wallora.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UnsplashApiService {
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30,
        @Query("orientation") orientation: String = "portrait",
        @Header("Authorization") clientAuth: String = "Client-ID YR3_bJ9inqhrXAurnX4tVCG5MFZmfdrrBFZ6RIb7G1w"
    ): Response<UnsplashSearchResponse>

    @GET("photos")
    suspend fun getEditorialPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30,
        @Query("order_by") orderBy: String = "popular",
        @Header("Authorization") clientAuth: String = "Client-ID YR3_bJ9inqhrXAurnX4tVCG5MFZmfdrrBFZ6RIb7G1w"
    ): Response<List<UnsplashPhotoDto>>
}
