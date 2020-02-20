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

    //Declarations and getters for Poko datasets
    private val searchResults = MutableLiveData<PokoGithubSearchResults>()

    fun getSearchResults(): LiveData<PokoGithubSearchResults> {
        return searchResults
    }

    private val githubUser = MutableLiveData<PokoGithubUser>()

    fun getGithubUser(): LiveData<PokoGithubUser> {
        return githubUser
    }

    private val githubRepos = MutableLiveData<PokoGithubReposList>()

    fun getGithubRepos(): LiveData<PokoGithubReposList> {
        return githubRepos
    }


    //Get info from network calls

    fun getGithubSearchResults(user: String) {

        val network = Network(baseApiUrl)
        network.initRetrofit().getSearchResults(user)
            .enqueue(object : Callback<PokoGithubSearchResults> {
                override fun onResponse(
                    call: Call<PokoGithubSearchResults>,
                    response: Response<PokoGithubSearchResults>
                ) {
                    println("success")
                    println(response.body().toString())
                    searchResults.value = response.body()
                }

                override fun onFailure(call: Call<PokoGithubSearchResults>, t: Throwable) {
                    println("failure")
                    t.printStackTrace()
                }
            })
    }




}
