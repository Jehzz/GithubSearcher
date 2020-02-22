package com.example.githubsearcher.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubsearcher.R
import com.example.githubsearcher.model.PokoGithubUser
import com.example.githubsearcher.viewmodel.GithubViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_view.*

class UserViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_view)

        //get selected user's login name. necessary for 2nd retrofit call
        var userName = intent.getStringExtra("user")

        //Declare and instantiate the viewmodel
        val githubViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return GithubViewModel() as T
            }
        }).get(GithubViewModel::class.java)


        //Fetch data via API. Compiler insists ? is not necessary, but it avoids a null ptr
        githubViewModel.getUserInfo(userName)

        //Observe data, assign to views
        githubViewModel.getGithubUser()
            .observe(this, Observer<PokoGithubUser> {
                tv_username.text = it.login?.toString()
                tv_email.text = "Email: " +it.email?.toString()
                tv_location.text = "Location: " +it.location?.toString()
                tv_join_date.text = "Joined: " +it.created_at?.toString()
                tv_follower_count.text = "Followers: " +it.followers?.toString()
                tv_following_count.text = "Following: " +it.following?.toString()
                Picasso.get().load(it.avatar_url?.toString()).resize(100, 100).into(iv_user_avatar)
                tv_user_bio.text = it.bio?.toString()
            })

        //todo: initiate 3rd retrofit api call with user's repolist url
    }
}
