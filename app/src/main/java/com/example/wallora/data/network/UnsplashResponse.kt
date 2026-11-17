package com.example.wallora.data.network

import com.google.gson.annotations.SerializedName

data class UnsplashSearchResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: List<UnsplashPhotoDto>
)

data class UnsplashPhotoDto(
    @SerializedName("id") val id: String,
    @SerializedName("description") val description: String?,
    @SerializedName("alt_description") val altDescription: String?,
    @SerializedName("urls") val urls: UnsplashUrlsDto,
    @SerializedName("user") val user: UnsplashUserDto,
    @SerializedName("width") val width: Int?,
    @SerializedName("height") val height: Int?,
    @SerializedName("color") val color: String?,
    @SerializedName("likes") val likes: Int?
)

data class UnsplashUrlsDto(
    @SerializedName("raw") val raw: String,
    @SerializedName("full") val full: String,
    @SerializedName("regular") val regular: String,
    @SerializedName("small") val small: String,
    @SerializedName("thumb") val thumb: String
)

data class UnsplashUserDto(
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("profile_image") val profileImage: UnsplashUserProfileImageDto?
)

data class UnsplashUserProfileImageDto(
    @SerializedName("small") val small: String?,
    @SerializedName("medium") val medium: String?
)
