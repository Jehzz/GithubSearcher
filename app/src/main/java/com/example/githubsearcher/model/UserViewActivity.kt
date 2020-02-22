package com.example.githubsearcher.model

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubsearcher.R
import com.example.githubsearcher.viewmodel.GithubViewModel
import kotlinx.android.synthetic.main.activity_user_view.*

class UserViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_view)

        //Declare and instantiate the viewmodel
        val githubViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return GithubViewModel() as T
            }
        }).get(GithubViewModel::class.java)

        //Fetch data via API todo: get User: String from intent
        githubViewModel.getUserInfo("Jehzz")

        //Observe data, assign to viewsc
        githubViewModel.getGithubUser()
            .observe(this, Observer<PokoGithubUser> {
                tv_username.text = it.login.toString()
                //tv_email.text = it.email.toString()
                //tv_location.text = it.location.toString()
                //tv_join_date.text = it.created_at.toString()
                //tv_follower_count.text = it.followers.toString()
                //tv_following_count.text = it.following.toString()
                //tv_bio....
            })



    }
}
