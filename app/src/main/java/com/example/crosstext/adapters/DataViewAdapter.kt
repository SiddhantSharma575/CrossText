package com.example.crosstext.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crosstext.ContentActivity
import com.example.crosstext.R
import com.example.crosstext.db.CopyData
import com.example.crosstext.fragments.DataFragment

class DataViewAdapter(private val context: Context,private var list:List<CopyData>) : RecyclerView.Adapter<DataViewAdapter.DataViewHolder>() {

    inner class DataViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val titleRecTV:TextView = itemView.findViewById(R.id.recTitleTV)
        val textRecTv:TextView = itemView.findViewById(R.id.mainTextRec)
        val deleteRecBtn:ImageView = itemView.findViewById(R.id.deleteBtn)
        val recBookMarkBtn:ImageView = itemView.findViewById(R.id.recBookMarkBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.save_item,parent,false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentModel = list[position]
        holder.titleRecTV.text = currentModel.title
        holder.textRecTv.text = currentModel.text

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ContentActivity::class.java)
            intent.putExtra("title",currentModel.title)
            intent.putExtra("text",currentModel.text)
            context.startActivity(intent)
        }
    }

    fun setData(noteList:List<CopyData>){
        this.list = noteList
    }

    override fun getItemCount(): Int {
        return list.size
    }

}