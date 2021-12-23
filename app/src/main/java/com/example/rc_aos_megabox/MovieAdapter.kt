package com.example.rc_aos_megabox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rc_aos_megabox.tablayout.BoxOfficeFragment

class MovieAdapter(context: BoxOfficeFragment): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    var datas = mutableListOf<MovieData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val title : TextView = itemView.findViewById(R.id.tv_movieNm)
        private val rank : TextView = itemView.findViewById(R.id.tv_rank)

        fun bind(item: MovieData){
            title.text = item.title
            rank.text = item.rank
        }
    }


}