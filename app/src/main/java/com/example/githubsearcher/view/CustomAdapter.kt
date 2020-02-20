package com.example.weatherapp.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearcher.R
import com.example.githubsearcher.model.PokoGithubSearchResults

/**
 * Class specific for returning only the next 24 hours of weather data to a recyclerview
 * @author: Jess Osborn
 */
class CustomAdapter(val dataSet: PokoGithubSearchResults) :
    RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

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
     * Returns the number of returned items from the dataset
     * @author: Jess Osborn
     */
    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(dataSet, position)
    }

    /**
     * Binds data from the dataset to search_item_layout views.
     * @author: Jess Osborn
     */
    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_user_name)

        fun onBind(data: PokoGithubSearchResults, position: Int) {
            tvName.text = data.items[0].login
        }
    }
}