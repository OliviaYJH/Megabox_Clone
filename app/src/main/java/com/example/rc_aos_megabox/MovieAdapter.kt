package com.example.rc_aos_megabox

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
import com.bumptech.glide.request.RequestOptions
import com.example.rc_aos_megabox.tablayout.BoxOfficeFragment
import kotlin.coroutines.coroutineContext

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
        private val buy: TextView = itemView.findViewById(R.id.tv_buy)
        private val star: TextView = itemView.findViewById(R.id.tv_star)
        private val img: ImageView = itemView.findViewById(R.id.iv_poster)

        fun bind(item: MovieData){
            title.text = item.title
            rank.text = item.rank
            buy.text = "예매율 " + item.buy + "%"
            star.text = item.star
            img.setImageResource(item.img)

            /*
                Glide.with(itemView.context).load(item.img)
                    .apply(RequestOptions().override(160,230))
                    .into(img)
            */

            itemView.setOnClickListener {
                //Toast.makeText(itemView.context, item.id.toString(), Toast.LENGTH_SHORT).show()
                var intent = Intent(itemView.context, MovieInfoActivity::class.java)
                intent.putExtra("title", item.title)
                intent.putExtra("id", item.id.toString())
                intent.putExtra("rank", item.rank)
                intent.putExtra("buy", item.buy)
                intent.putExtra("star", item.star)
                intent.putExtra("movieCd", item.movieCd)
                itemView.context.startActivity(intent)
            }

        }
    }


}