package com.kec.apicalling

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val context: Context,private val news : List<Article>) : RecyclerView.Adapter<NewsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        val viewHolder = NewsViewHolder(view)
//        view.setOnClickListener {
//            listener.onClicked(news[viewHolder.adapterPosition])
//        }
        return NewsViewHolder(view)

    }


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article : Article = news[position]
        var currentPosition = news[position]
        holder.title.text = currentPosition.title
        holder.dicription.text = currentPosition.description
        holder.itemView.setOnClickListener{
//            Toast.makeText(context,article.title,Toast.LENGTH_LONG).show()
            val intent = Intent(context,DetailsActivity::class.java)
            intent.putExtra("URL",article.url)
            context.startActivity(intent)
        }

        Glide.with(holder.itemView.context).load(currentPosition.urlToImage).into(holder.image)

    }

    override fun getItemCount(): Int {
        return news.size
    }

}

class NewsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val title : TextView = itemView.findViewById(R.id.title)
    val dicription : TextView = itemView.findViewById(R.id.author)
    val image : ImageView = itemView.findViewById(R.id.image)
}

//interface onItemClicked{
//    fun onClicked(item: Article)
//}