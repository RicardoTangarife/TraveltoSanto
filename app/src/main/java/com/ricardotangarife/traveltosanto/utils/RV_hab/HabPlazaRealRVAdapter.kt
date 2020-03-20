package com.ricardotangarife.traveltosanto.utils.RV_hab

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ricardotangarife.traveltosanto.R
import com.ricardotangarife.traveltosanto.model.Habitacion
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_plazareal.view.*


//import kotlinx.android.synthetic.main.item_rv.view.*


class HabPlazaRealRVAdapter(
    var context: Context,
    var habPlazaRealList: ArrayList<Habitacion>
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
        val habitacion : Habitacion = habPlazaRealList[position]
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

        fun bindPlazaReal (habitacion: Habitacion){
            itemView.tv_tipo.text = habitacion.titulo
            itemView.tv_descripcion.text = habitacion.descripcion
            itemView.tv_precio.text = habitacion.precio.toString()
            Picasso.get().load(habitacion.imagen).into(itemView.img_habitacion);

            itemView.setOnClickListener{
                //Toast.makeText(context, "Pasa a una actividad, donde esta el formulario para reservar", Toast.LENGTH_SHORT).show()
                var intent = Intent(context, Form_reservarActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("id_hab", habitacion.id)
                intent.putExtra("Tipo", habitacion.titulo)
                intent.putExtra("Descripcion", habitacion.descripcion)
                intent.putExtra("Precio", habitacion.precio)
                intent.putExtra("foto", habitacion.imagen)
                context.startActivity(intent)
            }
        }
    }
}

