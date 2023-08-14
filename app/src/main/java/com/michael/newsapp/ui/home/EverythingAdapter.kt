package com.michael.newsapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.michael.newsapp.R
import com.michael.newsapp.databinding.EverythingItemBinding
import com.michael.newsapp.model.ArticlesItem
import java.text.SimpleDateFormat
import java.util.Locale

class EverythingAdapter(private val articles: MutableList<ArticlesItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ARTICLE = 0
    private val VIEW_TYPE_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ARTICLE) {
            val binding = EverythingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        } else {
            // Inflate item_loading.xml
            val loadingView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(loadingView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            if (position < articles.size){
                val article = articles[position]
                holder.bind(article)
            }
        }
    }

    override fun getItemCount(): Int{
        return articles.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < articles.size) {
            VIEW_TYPE_ARTICLE
        } else {
            VIEW_TYPE_LOADING
        }
    }

    class ViewHolder(private val binding: EverythingItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticlesItem) {
            binding.txtEverythingTitle.text = article.title
            binding.txtEverythingAuthor.text = article.author

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH)

            val date = inputFormat.parse(article.publishedAt.toString())

            binding.txtEverythingDate.text = outputFormat.format(date)

            Glide.with(itemView.context)
                .load(article.urlToImage)
                .into(binding.imgEverythingNews)
        }
    }
}