package com.example.githubsearcher.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearcher.R
import com.example.githubsearcher.model.PokoGithubSearchResults
import com.example.githubsearcher.viewmodel.GithubViewModel
import com.example.weatherapp.view.SearchListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Thread.sleep

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

        //set listener on edit text
        et_username_search.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                sleep(100)
                githubViewModel.getGithubSearchResults(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //todo
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //todo

            }
        })


        //Observe the search results dataset and pass to recyclerview
        githubViewModel.getSearchResults()
            .observe(this, Observer<PokoGithubSearchResults> { t ->
                rv_search_results.layoutManager = LinearLayoutManager(
                    this@MainActivity
                )
                t?.let {
                    rv_search_results.adapter = SearchListAdapter(it, { userUrl: String -> itemClicked(userUrl) })
                }
            }
            )
    }

    //executed when recyclerview item is clicked. returns a user info api url, launches next activity
    private fun itemClicked(userName: String) {
        val intent = Intent(this, UserViewActivity::class.java).apply{
            putExtra("user", userName)
        }
        startActivity(intent)
    }

}
