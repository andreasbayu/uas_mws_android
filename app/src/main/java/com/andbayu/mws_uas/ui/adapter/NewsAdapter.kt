package com.andbayu.mws_uas.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andbayu.mws_uas.R
import com.andbayu.mws_uas.data.model.NewsModel
import com.andbayu.mws_uas.ui.view.DetailNewsActivity
import com.bumptech.glide.Glide

class NewsAdapter(private val listNews: ArrayList<NewsModel>)
    : RecyclerView.Adapter<NewsAdapter.VHNews>() {
    inner class VHNews(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvTag: TextView = itemView.findViewById(R.id.tv_tag)
        val tvAuthor: TextView = itemView.findViewById(R.id.tv_author)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        val tvDesc: TextView = itemView.findViewById(R.id.tv_desc)
        val imgThumb: ImageView =itemView.findViewById(R.id.img_thumb)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.VHNews {
        val layoutInflater: View = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return VHNews(layoutInflater)
    }

    override fun onBindViewHolder(holder: NewsAdapter.VHNews, position: Int) {
        val item = listNews[position]

        holder.tvTitle.text     = item.title
        holder.tvAuthor.text    = "Author: ${item.author}"
        holder.tvTag.text       = "Tag: ${item.tag}"
        holder.tvTime.text      = "Date: ${item.time}"
        holder.tvDesc.text      = "Description: ${item.desc.take(130).replace("\n", "")}..."

        Glide
            .with(holder.itemView.context)
            .load(item.thumb)
            .centerCrop()
            .into(holder.imgThumb)

        holder.itemView.setOnClickListener {
            val context = it.context

            val intent = Intent(context, DetailNewsActivity::class.java)
            intent.putExtra("KEY", item.key)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int = listNews.size
}