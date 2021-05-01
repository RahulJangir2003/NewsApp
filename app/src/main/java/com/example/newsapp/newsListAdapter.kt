package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class newsListAdapter(private val listner:newsItemCliked): RecyclerView.Adapter<newsViewHolder>() {
    private val items:ArrayList<news> =  ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        val viewHolder = newsViewHolder(view)
        view.setOnClickListener {
            listner.onClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: newsViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        if(item.author=="null"){
            holder.author.text = "Author unknown"
        }else{
        holder.author.text = item.author}
        Glide.with(holder.itemView.context).load(item.imgUrl).into(holder.image)
    }
    fun updateNews(updetedNews:ArrayList<news>){
        items.clear()
        items.addAll(updetedNews)
        notifyDataSetChanged()
    }

}
class newsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
   val title:TextView =  itemView.findViewById(R.id.title)
    val image:ImageView = itemView.findViewById(R.id.newsImg)
    val author:TextView = itemView.findViewById(R.id.author)
}
interface newsItemCliked{
    fun onClicked(item:news)

}