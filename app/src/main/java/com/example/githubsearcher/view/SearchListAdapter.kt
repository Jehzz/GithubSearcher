package com.example.weatherapp.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearcher.R
import com.example.githubsearcher.model.PokoGithubSearchResults
import com.squareup.picasso.Picasso

/**
 * Adapter class for UserViewActivity's repository recyclerview
 * @author: Jess Osborn
 */
class SearchListAdapter(val dataSet: PokoGithubSearchResults, val clickListener: (String) -> Unit) :
    RecyclerView.Adapter<SearchListAdapter.CustomViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.search_item_layout,
                    parent,
                    false
                )
        )

    /**
     * Returns the number of items currently in the dataset
     * @author: Jess Osborn
     */
    override fun getItemCount(): Int = dataSet.items.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(dataSet, position, clickListener)
    }


    /**
     * Binds data from the dataset to repos_item_view layout's views.
     * @author: Jess Osborn
     */
    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_user_name)
        var ivUserAvatar: ImageView = itemView.findViewById(R.id.iv_profile_avatar)
        var userName: String = ""
        var tvReposCount: TextView = itemView.findViewById(R.id.tv_repo_count)

        fun onBind(data: PokoGithubSearchResults, position: Int, clickListener: (String) -> Unit) {
            tvName.text = data.items[position].login
            tvReposCount.text = "Repos ## : NOT FOUND" //TODO: how to get this data without creating an API call for each match?
            Picasso.get().load(data.items[position].avatar_url).resize(200, 200).into(ivUserAvatar)
            userName = data.items[position].login
            itemView.setOnClickListener { clickListener(userName)}
        }
    }
}