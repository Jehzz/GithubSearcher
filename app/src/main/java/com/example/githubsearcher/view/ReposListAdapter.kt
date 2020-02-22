import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearcher.R
import com.example.githubsearcher.model.PokoGithubReposList

/**
 * Adapter class for 2nd activity's repolist recyclerview
 * @author: Jess Osborn
 */
class ReposListAdapter(val dataSet: PokoGithubReposList, val clickListener: (String) -> Unit) :
    RecyclerView.Adapter<ReposListAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.repos_item_view,
                    parent,
                    false
                )
        )

    /**
     * Returns the number of items currently in the dataset
     * @author: Jess Osborn
     */
    override fun getItemCount(): Int = dataSet.reposList.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(dataSet, position, clickListener)
    }


    /**
     * Binds data from the dataset to item_layout views.
     * @author: Jess Osborn
     */
    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvRepoName: TextView = itemView.findViewById(R.id.tv_repo_name)

        fun onBind(data: PokoGithubReposList, position: Int, clickListener: (String) -> Unit) {
            tvRepoName.text = data.reposList[position].toString()
        }
    }
}