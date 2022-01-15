package com.andbayu.mws_uas.data.model


import com.google.gson.annotations.SerializedName

data class DetailNewsModel(
    @SerializedName("method")
    val method: String,
    @SerializedName("results")
    val results: Results,
    @SerializedName("status")
    val status: Boolean
) {
    data class Results(
        @SerializedName("author")
        val author: String,
        @SerializedName("categories")
        val categories: List<String>,
        @SerializedName("content")
        val content: List<String>,
        @SerializedName("date")
        val date: String,
        @SerializedName("figure")
        val figure: List<Any>,
        @SerializedName("thumb")
        val thumb: String,
        @SerializedName("title")
        val title: String
    )
}