package app.khodko.feedcat.ui.results

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.khodko.feedcat.database.entity.GameResult
import app.khodko.feedcat.R

class ResultListAdapter : ListAdapter<GameResult, ResultListAdapter.ResultViewHolder>(RESULTS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(gameResult: GameResult?) {
            wordItemView.text = gameResult?.satiety.toString()
        }

        companion object {
            fun create(parent: ViewGroup): ResultViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return ResultViewHolder(view)
            }
        }
    }

    companion object {
        private val RESULTS_COMPARATOR = object : DiffUtil.ItemCallback<GameResult>() {
            override fun areItemsTheSame(oldItem: GameResult, newItem: GameResult): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: GameResult, newItem: GameResult): Boolean {
                return oldItem.satiety == newItem.satiety
            }
        }
    }
}
