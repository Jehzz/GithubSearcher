package com.example.githubsearcher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubsearcher.model.Network
import com.example.githubsearcher.model.PokoGithubReposList
import com.example.githubsearcher.model.PokoGithubSearchResults
import com.example.githubsearcher.model.PokoGithubUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubViewModel : ViewModel() {

    val baseApiUrl: String = "https://api.github.com/search/"
    val userBaseUrl: String = "https://api.github.com/"

    //Declarations and getters for Poko datasets
    private val searchResults = MutableLiveData<PokoGithubSearchResults>()

    fun getSearchResults(): LiveData<PokoGithubSearchResults> {
        return searchResults
    }

    private val githubUser = MutableLiveData<PokoGithubUser>()

    fun getGithubUser(): LiveData<PokoGithubUser> {
        return githubUser
    }

    private val githubRepos = MutableLiveData<List<PokoGithubReposList>>()

    fun getGithubRepos(): LiveData<List<PokoGithubReposList>> {
        return githubRepos
    }


    //Get search results from retrofit
    fun getGithubSearchResults(user: String) {

        val network = Network(baseApiUrl)
        network.initRetrofit().getSearchResults(user)
            .enqueue(object : Callback<PokoGithubSearchResults> {
                override fun onResponse(
                    call: Call<PokoGithubSearchResults>,
                    response: Response<PokoGithubSearchResults>
                ) {
                    println("success")
                    searchResults.value = response.body()
                    // call getUserInfo() here with retrieved user url's
                    // save to database
                }
                override fun onFailure(call: Call<PokoGithubSearchResults>, t: Throwable) {
                    println("failure")
                    t.printStackTrace()
                }
            })
    }

    //Get user info from retrofit
    fun getUserInfo(user: String) {

        val network = Network(userBaseUrl)
        network.initRetrofit().getUserInfo(user)
            .enqueue(object : Callback<PokoGithubUser> {
                override fun onResponse(
                    call: Call<PokoGithubUser>,
                    response: Response<PokoGithubUser>
                ) {
                    println("success")
                    println("response info = " +response.body())
                    githubUser.value = response?.body()
                    println("Dataset info = " +getGithubUser().toString())

                }
                override fun onFailure(call: Call<PokoGithubUser>, t: Throwable) {
                    println("failure")
                    t.printStackTrace()
                }
            })
    }

    //Get user's repo list from retrofit
    fun getUserRepos(user: String) {

        val network = Network(baseApiUrl)
        network.initRetrofit().getRepos(user)
            .enqueue(object : Callback<List<PokoGithubReposList>> {
                override fun onResponse(
                    call: Call<List<PokoGithubReposList>>,
                    response: Response<List<PokoGithubReposList>>
                ) {
                    println("success")
                    println("response info = " +response.body())
                    githubRepos.value = response?.body()

                }
                override fun onFailure(call: Call<List<PokoGithubReposList>>, t: Throwable) {
                    println("failure")
                    t.printStackTrace()
                }
            })
    }
}
