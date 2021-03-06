package com.example.githubsearcher.view

import ReposListAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearcher.R
import com.example.githubsearcher.model.PokoGithubReposList
import com.example.githubsearcher.model.PokoGithubUser
import com.example.githubsearcher.viewmodel.GithubViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_view.*

/**
 * This class reads the selected Github user from the intent bundle, and displays their
 * profile information and repository list from the viewmodel
 * @author: Jess Osborn
 */
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


        //Fetch github user data via API. Compiler insists ? is not necessary, but it avoids a null ptr
        githubViewModel.getUserInfo(userName)

        //Observe data, assign to viewsc
        githubViewModel.getGithubUser()
            .observe(this, Observer<PokoGithubUser> {
                tv_username.text = it.login?.toString()
                tv_email.text = "Email: " + it.email?.toString()
                tv_location.text = "Location: " + it.location?.toString()
                tv_join_date.text = "Joined: " + it.created_at?.toString().substring(0, 10)
                tv_follower_count.text = "Followers: " + it.followers?.toString()
                tv_following_count.text = "Following: " + it.following?.toString()
                Picasso.get().load(it.avatar_url?.toString()).resize(200, 200).into(iv_user_avatar)
                tv_user_bio.text = it.bio?.toString()
            })


        //Fetch github user repository data via API
        githubViewModel.getUserRepos(userName)

        //Observe repository data, pass to recyclerview
        githubViewModel.getGithubRepos()
            .observe(this, Observer<List<PokoGithubReposList>> { t ->
                rv_user_repos.layoutManager = LinearLayoutManager(
                    this@UserViewActivity
                )
                t?.let {
                    rv_user_repos.adapter = ReposListAdapter(it, { repoUrl: String -> itemClicked(repoUrl) })
                }
            })
    }

    /**
     * This method executes upon clicking a user's repository, opens repo's url in web browser
     * through implicit intent
     * @author: Jess Osborn
     */
    //Executed when recyclerview item is clicked. opens repo url in web browser
    private fun itemClicked(repoUrl: String) {
        intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(repoUrl)
        startActivity(intent)
    }
}
