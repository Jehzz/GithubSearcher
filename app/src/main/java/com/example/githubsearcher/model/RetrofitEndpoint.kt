package com.example.githubsearcher.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Retrofit creates the correct API call and maps the returned data to the Poko classes
 */
interface RetrofitEndpoint {


    //Example: https://api.github.com/search/users?q=square
    @GET("users")
    fun getSearchResults(
        @Query("q") searchTerm:String
    ): Call<PokoGithubSearchResults>


    //Example: https://api.github.com/users/square
    @GET("users")
    fun getUserInfo(
        @Query("user") user:String
    ): Call<PokoGithubUser>

    //Example: https://api.github.com/users/square/repos
    @GET("/users/{username}/repos")
    fun getRepos(
        @Path("username") user:String
    ): Call<PokoGithubUser>
}