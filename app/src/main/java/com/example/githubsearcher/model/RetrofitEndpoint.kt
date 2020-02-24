package com.example.githubsearcher.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit creates the correct API calls to github and maps the returned data to the Poko classes
 * @author: Jess Osborn
 */
interface RetrofitEndpoint {

    /**
     * Returns list of github users that match the searched name
     * @author: Jess Osborn
     */
    //Example: https://api.github.com/search/users?q=square
    @GET("users")
    fun getSearchResults(
        @Query("q") searchTerm:String
    ): Call<PokoGithubSearchResults>


    /**
     * Returns profile information on the specified user
     * @author: Jess Osborn
     */
    //Example: https://api.github.com/users/square
    @GET("users/{username}")
    fun getUserInfo(
        @Path("username") user:String
    ): Call<PokoGithubUser>

    /**
     * Returns the repos list of the specified user
     * @author: Jess Osborn
     */
    //Example: https://api.github.com/users/square/repos
    @GET("/users/{username}/repos")
    fun getRepos(
        @Path("username") user:String
    ): Call<List<PokoGithubReposList>>
}