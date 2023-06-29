package com.android.rnews

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.rnews.data.RedditPost
import com.squareup.picasso.Picasso
import java.util.Date
import java.util.concurrent.TimeUnit

class PostAdapter : PagingDataAdapter<RedditPost,PostAdapter.PostViewHolder>(POST_COMPARATOR) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        if(post!=null){
            holder.author.text = post.author
            holder.createdUtc.text = time(post.createdUtc)
            holder.commetns.text = post.num_comments + " " + "comments"
            Picasso.get().load(post.thumbnail).error(R.drawable.ic_image_e).into(holder.thumbnail)
        }

    }

    private fun time(createdUtc:Long): String {
        val date = Date(createdUtc * 1000)
        val now = Date()
        val hoursAgo = TimeUnit.MILLISECONDS.toHours(now.time - date.time)
        return "$hoursAgo hours ago"
    }


    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val author: TextView = view.findViewById(R.id.author)
        val createdUtc: TextView = view.findViewById(R.id.createdUtc)
        val thumbnail: ImageView = view.findViewById(R.id.thumbnail)
        val commetns:TextView = view.findViewById(R.id.comment_count)
    }

    companion object {
        private val POST_COMPARATOR = object : DiffUtil.ItemCallback<RedditPost>() {
            override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
                return oldItem.author == newItem.author
            }

            override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean {
                return oldItem == newItem
            }
        }
    }
}