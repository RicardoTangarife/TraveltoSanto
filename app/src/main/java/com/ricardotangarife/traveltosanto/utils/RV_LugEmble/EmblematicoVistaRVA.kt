package com.ricardotangarife.traveltosanto.utils.RV_LugEmble

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ricardotangarife.traveltosanto.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_emblematicovista.view.*

class EmblematicoVistaRVA (
    var context: Context,
    var emblematicoList: ArrayList<EmblematicoVista>
): RecyclerView.Adapter<EmblematicoVistaRVA.EmblemaViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmblematicoVistaRVA.EmblemaViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.item_emblematicovista, parent, false)
        return EmblemaViewHolder(itemView, context)
    }

    override fun getItemCount(): Int = emblematicoList.size

    override fun onBindViewHolder(holder: EmblemaViewHolder, position: Int) {
        val emblema : EmblematicoVista = emblematicoList[position]
        holder.bindEmblema(emblema)
    }

    class EmblemaViewHolder(
        itemView : View,
        context: Context
    ): RecyclerView.ViewHolder(itemView){
        private var context: Context

        init {
            this.context = context
        }

        fun bindEmblema(emblema: EmblematicoVista){
            Picasso.get().load(emblema.imagen).into(itemView.img_vista)

        }
    }
}

