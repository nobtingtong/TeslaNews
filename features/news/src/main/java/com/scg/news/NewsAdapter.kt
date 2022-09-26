package com.scg.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.scg.core.extension.toDateTimeDisplay
import com.scg.data.model.remote.Article
import com.scg.news.databinding.ItemNewsBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolderNews>(), Filterable {
    var onItemClick: ((Article) -> Unit)? = null

    private var listData: ArrayList<Article> = arrayListOf()
    private var listDataFiltered: ArrayList<Article> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(listData: List<Article>) {
        this.listData.addAll(listData)
        this.listDataFiltered.addAll(listData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderNews {
        val view: ItemNewsBinding =
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderNews(view)
    }

    override fun onBindViewHolder(holder: ViewHolderNews, position: Int) {
        holder.bind(listDataFiltered[position])
    }

    override fun getItemCount(): Int {
        return listDataFiltered.size
    }

    inner class ViewHolderNews(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.imgBanner.load(article.urlToImage)
            binding.textTitle.text = article.title
            binding.textDescription.text = article.description
            ("Updated: " + article.publishedAt.toDateTimeDisplay()).also {
                binding.textDate.text = it
            }
            binding.root.setOnClickListener {
                onItemClick?.invoke(article)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) listDataFiltered = listData else {
                    val filteredList = ArrayList<Article>()
                    listData
                        .filter {
                            (it.title.contains(constraint!!, true)) or
                                    (it.description.contains(constraint, true))

                        }
                        .forEach { filteredList.add(it) }
                    listDataFiltered = filteredList
                }
                return FilterResults().apply { values = listDataFiltered }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listDataFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Article>
                notifyDataSetChanged()

            }
        }
    }
}
