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
 * Adapter class for mainactivity's search results recyclerview
 * @author: Jess Osborn
 */
class SearchListAdapter(val dataSet: PokoGithubSearchResults) :
    RecyclerView.Adapter<SearchListAdapter.CustomViewHolder>() {

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
    override fun getItemCount(): Int = dataSet.total_count

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(dataSet, position)
    }

    /**
     * Binds data from the dataset to search_item_layout views.
     * @author: Jess Osborn
     */
    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_user_name)
        var ivUserAvatar: ImageView = itemView.findViewById(R.id.iv_user_avatar)

        fun onBind(data: PokoGithubSearchResults, position: Int) {
            tvName.text = data.items[position].login
            Picasso.get().load(data.items[position].avatar_url).resize(100, 100).into(ivUserAvatar)
        }
    }
}