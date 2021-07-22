package org.techtown.catsby.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.comment.view.*
import org.techtown.catsby.R

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.ViewHolder>(){
    var items = ArrayList<Comment>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.comment, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        fun setItem(item:Comment){
            itemView.commentcontents.text = item.commentContents
            itemView.commenttitle.text = item.commentTitle
        }
    }
}