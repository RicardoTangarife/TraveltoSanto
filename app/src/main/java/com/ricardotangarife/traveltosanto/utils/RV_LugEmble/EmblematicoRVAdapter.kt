package com.ricardotangarife.traveltosanto.utils.RV_LugEmble

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ricardotangarife.traveltosanto.R
import kotlinx.android.synthetic.main.item_emblematico.view.*

class EmblematicoRVAdapter(
    var context: Context,
    var emblematicoList: ArrayList<Emblematico>
): RecyclerView.Adapter<EmblematicoRVAdapter.EmblematicoViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmblematicoViewHolder {
       var itemView = LayoutInflater.from(context).inflate(R.layout.item_emblematico, parent, false)
       return EmblematicoViewHolder(itemView, context)
    }

    override fun getItemCount(): Int = emblematicoList.size

    override fun onBindViewHolder(holder: EmblematicoViewHolder, position: Int) {
        val emblematico : Emblematico = emblematicoList[position]
        holder.bindEmblematico(emblematico)
    }

    class EmblematicoViewHolder(
        itemView: View,
        context: Context):
            RecyclerView.ViewHolder(itemView){
        private var context: Context

        init {
            this.context = context
        }

        fun bindEmblematico (emblematico : Emblematico) {
            itemView.tv_emblematico.text = emblematico.title_emblematico
            itemView.img_vista.setImageResource(emblematico.img_emblematico)

            itemView.setOnClickListener{
                var intent = Intent(context, EmblemaDetalleActivity::class.java )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("img_title", emblematico.title_emblematico)
                intent.putExtra("img", emblematico.img_emblematico)
                intent.putExtra("name_data", emblematico.name_data)
                context.startActivity(intent)
            }
        }

    }

}