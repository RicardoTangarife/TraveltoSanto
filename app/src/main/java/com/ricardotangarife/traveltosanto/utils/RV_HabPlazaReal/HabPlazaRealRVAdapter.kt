package com.ricardotangarife.traveltosanto.utils.RV_HabPlazaReal

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.ricardotangarife.traveltosanto.R
import kotlinx.android.synthetic.main.item_plazareal.view.*

//import kotlinx.android.synthetic.main.item_rv.view.*


class HabPlazaRealRVAdapter (
    var context: Context,
    var habPlazaRealList: ArrayList<HabPlazaReal>
)   : RecyclerView.Adapter<HabPlazaRealRVAdapter.HabPlazaRealViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabPlazaRealViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.item_plazareal, parent, false)
        return HabPlazaRealViewHolder(itemView, context)
    }

    override fun getItemCount(): Int = habPlazaRealList.size

    override fun onBindViewHolder(
        holder: HabPlazaRealViewHolder,
        position: Int) {
        val habitacion : HabPlazaReal = habPlazaRealList[position]
         holder.bindPlazaReal(habitacion)

    }

    class HabPlazaRealViewHolder (
        itemView: View,
        context: Context
    ) : RecyclerView.ViewHolder(itemView){

        private var context: Context

        init {
            this.context = context
        }

        fun bindPlazaReal (habitacion: HabPlazaReal){
            itemView.tv_tipo.text = habitacion.Tipo
            itemView.tv_descripcion.text = habitacion.Descripcion
            itemView.tv_precio.text = habitacion.precio
            itemView.img_habitacion.setImageResource(habitacion.foto)
            itemView.setOnClickListener{
                Toast.makeText(context, "Pasa a una actividad, donde esta el formulario para reservar", Toast.LENGTH_SHORT).show()
       /*         var intent = Intent(this, Form_reservarActivity::class.java)
                intent.putExtra("Tipo", habitacion.Tipo)
                startActivity(intent)
                */

            }

        }

    }
}

