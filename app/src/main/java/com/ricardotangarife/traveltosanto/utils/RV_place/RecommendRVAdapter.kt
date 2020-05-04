package com.ricardotangarife.traveltosanto.utils.RV_place

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ricardotangarife.traveltosanto.R
import com.ricardotangarife.traveltosanto.utils.RV_LugEmble.Emblematico
import com.ricardotangarife.traveltosanto.utils.RV_LugEmble.TurismoDetalleActivity
import kotlinx.android.synthetic.main.item_emblematico.view.*


class RecommendRVAdapter (
    var context: Context,
    var recommendList: ArrayList<Recommend>
) : RecyclerView.Adapter<RecommendRVAdapter.RecommendViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): RecommendViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.item_emblematico, parent, false)
        return RecommendViewHolder(itemView, context)
    }

    override fun getItemCount(): Int = recommendList.size

    override fun onBindViewHolder(
        holder: RecommendViewHolder,
        position: Int) {
        val recommend : Recommend = recommendList[position]
        holder.bindRecommend(recommend)
    }

    class RecommendViewHolder(
        itemView: View,
        context: Context
    ): RecyclerView.ViewHolder(itemView){
        private var context: Context

        init {
            this.context = context
        }

        fun bindRecommend (recommend: Recommend){
            itemView.tv_emblematico.text = recommend.title_recommend
            itemView.img_vista.setImageResource(recommend.img_Recommend)

            itemView.setOnClickListener{
                var intent = Intent(context, TurismoDetalleActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("img_title", recommend.title_recommend)
                intent.putExtra("img", recommend.img_Recommend)
                intent.putExtra("name_data", recommend.name_data)
                intent.putExtra("name_data2", recommend.name_data2)

                context.startActivity(intent)


            }


        }
    }


}