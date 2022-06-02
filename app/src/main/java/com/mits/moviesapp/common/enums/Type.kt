package com.mits.moviesapp.common.enums

import com.google.gson.annotations.SerializedName

enum class Type {
    @SerializedName("tv")
    TV,

    @SerializedName("movie")
    MOVIE,

    @SerializedName("person")
    PERSON,
}