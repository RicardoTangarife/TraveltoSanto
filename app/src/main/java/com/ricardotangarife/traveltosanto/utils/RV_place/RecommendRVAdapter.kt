package com.ricardotangarife.traveltosanto.utils.RV_place

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.ricardotangarife.traveltosanto.R

class RecommendRVAdapter (
    var context: Context,
    var recommendList: ArrayList<Recommend>
) : RecyclerView.Adapter<RecommendRVAdapter.RecommendViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): RecommendViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.item_plazareal, parent, false)
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

        }
    }


}