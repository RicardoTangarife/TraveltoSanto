package com.ricardotangarife.traveltosanto.model.room

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ricardotangarife.traveltosanto.R
import kotlinx.android.synthetic.main.item_profile.view.*

class ReservasRVAdapter(
    var context: Context,
    var reservasList: ArrayList<Reserva>
) : RecyclerView.Adapter<ReservasRVAdapter.ReservasViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservasViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false)
        return ReservasViewHolder(itemView, context)
    }

    override fun getItemCount(): Int = reservasList.size

    override fun onBindViewHolder(holder: ReservasViewHolder, position: Int) {
        val reserva: Reserva = reservasList[position]
        holder.bindReserva(reserva)
    }

    class ReservasViewHolder(           //Esto se crea primero antes de implementar los metodos
        itemView: View,
        context: Context
    ) : RecyclerView.ViewHolder(itemView){

        fun bindReserva(reserva: Reserva){
            itemView.tv_nombre.text = reserva.titulo
            itemView.tv_descripcionr.text = reserva.descripcion
            itemView.tv_precior.text = reserva.precio.toString()
            itemView.tv_ingresor2.text = reserva.fecha_ingreso
            itemView.tv_salidar2.text = reserva.fecha_salida
        }
    }




}