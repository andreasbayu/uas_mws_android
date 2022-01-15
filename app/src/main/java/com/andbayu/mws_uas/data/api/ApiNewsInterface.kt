package com.andbayu.mws_uas.data.api

import com.andbayu.mws_uas.data.model.DetailNewsModel
import com.andbayu.mws_uas.data.model.NewsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiNewsInterface {

    @GET("games")
    fun getGamesNews(
        @Query("page")
        page: Int,
    ) : Call<ArrayList<NewsModel>>

    @GET("detail/{id}")
    fun getDetailNews(
        @Path("id", encoded = true)
        id: String
    ) : Call<DetailNewsModel>


    @GET("search")
    fun searchNews(
        @Query("search")
        query: String
    ) : Call<ArrayList<NewsModel>>

}