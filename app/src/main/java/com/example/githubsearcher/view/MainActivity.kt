package com.example.githubsearcher.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.githubsearcher.R
import com.example.githubsearcher.model.PokoGithubSearchResults
import com.example.githubsearcher.viewmodel.GithubViewModel
import com.example.weatherapp.view.CustomAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Declare and instantiate the viewmodel
        val githubViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return GithubViewModel() as T
            }
        }).get(GithubViewModel::class.java)

        //Fetch the data based on typed input. Test mine for now
        githubViewModel.getGithubSearchResults("Jehzz")

        //Observe the search results dataset and pass to recyclerview
        githubViewModel.getSearchResults()
            .observe(this, Observer<PokoGithubSearchResults> { t ->
                rv_search_results.layoutManager = GridLayoutManager(
                    this@MainActivity,
                    4
                )
                rv_search_results.adapter = CustomAdapter(t!!)
            }
            )

    }
}
