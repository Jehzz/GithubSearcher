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
import com.example.githubsearcher.model.PokoGithubSearchResults
import com.example.githubsearcher.viewmodel.GithubViewModel
import com.example.weatherapp.view.SearchListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * This class lets the user search for Github users, triggering the viewmodel to find matches and
 * populates the recyclerview with the results. The user can tap on a result to launch the 2nd
 * activity and see more information
 * @author: Jess Osborn
 */
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.githubsearcher.R.layout.activity_main)

        //Declare and instantiate the viewmodel
        val githubViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return GithubViewModel() as T
            }
        }).get(GithubViewModel::class.java)

        //set listener on edit text
        et_username_search.addTextChangedListener(object: TextWatcher {
            var timer = Timer()
            override fun afterTextChanged(s: Editable?) {
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        githubViewModel.getGithubSearchResults(s.toString())
                    }
                }, 600) // 600ms delay before the timer executes the „run“ method from TimerTask
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { //not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //if timer already exists, cancel and wait for user to finish typing
                if (timer != null)
                    timer.cancel()
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

    /**
     * Executed when recyclerview item is clicked. returns a user's login field, launches next activity
     * @author: Jess Osborn
     */
    private fun itemClicked(userName: String) {
        val intent = Intent(this, UserViewActivity::class.java).apply{
            putExtra("user", userName)
        }
        startActivity(intent)
    }
}
