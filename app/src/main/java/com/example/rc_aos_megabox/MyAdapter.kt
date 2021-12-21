package com.example.rc_aos_megabox

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter:RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var items: ArrayList<Map<String, Object>>?= null

    fun MyAdapter(resultList: ArrayList<Map<String, Object>>){
        this.items = resultList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item: Map<String, Object>
        item = items!!.get(position)
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    class MyViewHolder(var view:View): RecyclerView.ViewHolder(view){
        private var rank = view.findViewById<TextView>(R.id.tv_rank)
        private var movieNm = view.findViewById<TextView>(R.id.tv_movieNm)
        private var openDt = view.findViewById<TextView>(R.id.tv_openDt)

        fun setItem(item: Map<String, Object>){
            rank.setText(item.get("rank").toString())
            movieNm.setText(item.get("movieNm").toString())
            openDt.setText(item.get("openDt").toString())
        }
    }

}