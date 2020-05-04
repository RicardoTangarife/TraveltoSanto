package com.ricardotangarife.traveltosanto.utils.RV_LugEmble

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ricardotangarife.traveltosanto.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_turismo.view.*

class TurismoRVAdapter (
    var context: Context,
    var turismoList: ArrayList<Turismo>
): RecyclerView.Adapter<TurismoRVAdapter.TurismoViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TurismoRVAdapter.TurismoViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.item_turismo, parent, false)
        return TurismoViewHolder(itemView, context)
    }

    override fun getItemCount(): Int = turismoList.size

    override fun onBindViewHolder(holder: TurismoViewHolder, position: Int) {
        val turismo: Turismo = turismoList[position]
        holder.bindTurismo(turismo)
    }


    class TurismoViewHolder(
        itemView: View,
        context: Context
    ): RecyclerView.ViewHolder(itemView){
        private var context: Context

        init {
            this.context = context
        }

        fun bindTurismo(turismo: Turismo){
            Picasso.get().load(turismo.imagen).into(itemView.img_vista)
        }
    }
}