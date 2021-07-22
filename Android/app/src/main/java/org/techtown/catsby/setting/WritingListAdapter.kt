package org.techtown.catsby.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catsbe.writingList
import kotlinx.android.synthetic.main.writinglist.view.*
import org.techtown.catsby.R

class WritingListAdapter : RecyclerView.Adapter<WritingListAdapter.ViewHolder>(){
    var items = ArrayList<Writing>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    // 04/06 47:01
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.writinglist, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun setItem(item: Writing) {
            itemView.writingheader.text = item.title
            itemView.writingdate.text = item.date
        }
    }
}