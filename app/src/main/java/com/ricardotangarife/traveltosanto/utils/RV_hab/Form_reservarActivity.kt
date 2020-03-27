package com.ricardotangarife.traveltosanto.utils.RV_hab

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ricardotangarife.traveltosanto.LoginActivity
import com.ricardotangarife.traveltosanto.R
import com.ricardotangarife.traveltosanto.model.Habitacion
import com.ricardotangarife.traveltosanto.utils.botton_navegation.InicioActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_form_reservar.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class Form_reservarActivity : AppCompatActivity() {

    var Tipo = ""
    var Descripcion = ""
    var precio = 0
    var preciohab = 0
    var foto = ""
    var id_hab = ""
    private var cal = Calendar.getInstance()
    private lateinit var fecha1: String
    private lateinit var fecha2: String
    var correctFecha = false
    var d1 = ""
    var d2 = ""
    var dc = ""
    var ingreso = ""
    var salida = ""
    var inicio = ""
    var fin = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_reservar)

        val auth = FirebaseAuth.getInstance()
        val currentUser: FirebaseUser? = auth.currentUser

        val dataSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                var format = "dd/MM/yyyy"
                var sdf = SimpleDateFormat(format, Locale.US)
                var currentFecha =  sdf.format(Date())
                fecha1 = sdf.format(cal.time).toString()
                tv_showPicker.text = fecha1
                var date1 = sdf.parse(fecha1)
                var date2 = sdf.parse(currentFecha)
                val f = SimpleDateFormat(format, Locale.US)
                d1 = f.format(date1)
                dc = f.format(date2)
                if((d1>=dc)&&(d2.isNotEmpty())&&(d2>d1)){
                    var diff = sdf.parse(d2).time - sdf.parse(d1).time
                    var daysBetween = (diff / (1000*60*60*24));
                    tv_precioreserva.text = (((tv_precio.text.toString().toInt())/2)*daysBetween).toString()
                    correctFecha = true
                }
                else{
                    correctFecha = false
                }
            }

        val dataSetListener2 =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                var format = "dd/MM/yyyy"
                var sdf = SimpleDateFormat(format, Locale.US)
                var currentFecha =  sdf.format(Date())
                fecha2 = sdf.format(cal.time).toString()
                tv_showPicker2.text = fecha2
                var date1 = sdf.parse(fecha2)
                var date2 = sdf.parse(currentFecha)
                val f = SimpleDateFormat(format, Locale.US)
                d2 = f.format(date1)
                dc = f.format(date2)
                if((d2>=dc)&&(d1.isNotEmpty())&&(d2>d1)&&(d1>=dc)){
                    var diff = sdf.parse(d2).time - sdf.parse(d1).time
                    var daysBetween = (diff / (1000*60*60*24));
                    tv_precioreserva.text = (((tv_precio.text.toString().toInt())/2)*daysBetween).toString()
                    correctFecha = true
                }
                else{
                    correctFecha = false
                }
            }


        tv_showPicker.setOnClickListener {
            DatePickerDialog(
                this,
                dataSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
            ingreso = tv_showPicker.text.toString()
        }

        tv_showPicker2.setOnClickListener {
            fin = cal.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(
                this,
                dataSetListener2,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
            salida = tv_showPicker2.text.toString()
        }


        val datoshab = intent.extras
        if (datoshab != null) {
            Tipo = datoshab?.getString("Tipo").toString()
            Descripcion = datoshab?.getString("Descripcion").toString()
            precio = datoshab?.getInt("Precio")
            foto = datoshab?.getString("foto").toString()
            id_hab = datoshab?.getString("id_hab").toString()
        }
        preciohab = precio
        tv_tipo.text = Tipo
        tv_descripcion.text = Descripcion
        tv_precio.text = precio.toString()
        Picasso.get().load(foto).into(img_habitacion)
        tv_precioreserva.text = tv_precio.text

        bt_reserva.setOnClickListener {
            if (!(et_Nombre.text.toString().isEmpty()) && !(et_telefono_a.text.toString().isEmpty()) && !(et_cedula.text.toString().isEmpty()) && !(et_correo.text.toString().isEmpty()) && !(et_telefono_a.text.toString().isEmpty()) && (ingreso != "") && (salida != "")&&(correctFecha)&&(isEmailValid(et_correo.text.toString()))) {
                val nombre = et_Nombre.text.toString()
                val cc = et_cedula.text.toString().toInt()
                val correo = et_correo.text.toString()
                val precio = tv_precioreserva.text.toString().toInt()
                val telefono = et_telefono_a.text.toString()
                var correogood = false
                val user = FirebaseAuth.getInstance().currentUser
                if(user?.email == correo){
                    correogood = true
                }
                if(correogood){
                    val database = FirebaseDatabase.getInstance()
                    val myRef = database.getReference("reservas")
                    val idReservar = myRef.push().key
                    val reserva = com.ricardotangarife.traveltosanto.model.Reservar(
                        idReservar!!,
                        nombre,
                        id_hab,
                        cc,
                        ingreso,
                        salida,
                        precio,
                        telefono
                    )
                    myRef.child(idReservar).setValue(reserva)
                    var hab = Habitacion()
                    val Refhab = database.getReference("habitaciones")
                    Refhab.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            for (snapshot in dataSnapshot.children) {
                                hab = snapshot.getValue(Habitacion::class.java)!!
                                if (hab!!.id == id_hab) {
                                    val childUpdate = HashMap<String, Any>()
                                    childUpdate["visibilidad"] = false
                                    Refhab.child(hab.id).updateChildren(childUpdate)
                                }
                            }
                        }
                        override fun onCancelled(p0: DatabaseError) {
                            Log.w(
                                "Problemas",
                                "problema al encontrar el id de la habitacion",
                                p0.toException()
                            )
                        }
                    })
                    Toast.makeText(this, "Reserva Completa", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, InicioActivity::class.java)
                    intent.putExtra("frg", "profile")
                    this.startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, "Verifique su correo no coincide", Toast.LENGTH_SHORT).show()
                }
            }else {
                if(!correctFecha){
                    Toast.makeText(this, "Fecha inválida, revisa los campos", Toast.LENGTH_SHORT).show()
                }
                else if(!isEmailValid(et_correo.text.toString())){
                    Toast.makeText(this, "Correo no válido", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Debe digitar todos los campos, incluyendo fechas", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_abrir) {
            //Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show()
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            goToLoginActicity()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun goToLoginActicity(){
        var intentlogin = Intent(this, LoginActivity::class.java)
        intentlogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intentlogin)
    }
    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }
}

