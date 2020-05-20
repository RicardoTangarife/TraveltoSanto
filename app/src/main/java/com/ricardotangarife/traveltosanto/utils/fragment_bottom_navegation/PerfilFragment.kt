package com.ricardotangarife.traveltosanto.utils.fragment_bottom_navegation


import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.ricardotangarife.traveltosanto.LoginActivity

import com.ricardotangarife.traveltosanto.R
import com.ricardotangarife.traveltosanto.SesionRoom
import com.ricardotangarife.traveltosanto.model.Profile
import com.ricardotangarife.traveltosanto.model.room.Reserva
import com.ricardotangarife.traveltosanto.model.room.ReservaDAO
import com.ricardotangarife.traveltosanto.model.room.ReservasRVAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_perfil.*
import kotlinx.android.synthetic.main.fragment_perfil.view.*
import java.io.ByteArrayOutputStream
import java.util.HashMap

/**
 * A simple [Fragment] subclass.
 */
class PerfilFragment : Fragment() {

    val idUsuario = "primero"
    var usuarioext = false

    @SuppressLint("IntentReset")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_perfil, container, false)

        val auth = FirebaseAuth.getInstance()
        val currentUser: FirebaseUser? = auth.currentUser
        root.tv_correo.text = auth.currentUser?.email

        val rv_reservas : RecyclerView = root.findViewById<RecyclerView>(R.id.rv_tusreservas)
        rv_reservas.layoutManager = LinearLayoutManager(
            activity!!.applicationContext,
            RecyclerView.VERTICAL,
            false
        )
        rv_reservas.setHasFixedSize(true)
        var reservaDAO: ReservaDAO = SesionRoom.database.ReservaDAO()
        var allReservas: List<Reserva> = reservaDAO.getReservasUser(auth.currentUser?.uid.toString())
        if(allReservas.isEmpty()){
            root.tv_noreservas.visibility = VISIBLE
        }
        var reserRVAdapter = ReservasRVAdapter(
            activity!!.applicationContext,
            allReservas as ArrayList<Reserva>
        )
        rv_reservas.adapter = reserRVAdapter
        reserRVAdapter.notifyDataSetChanged()

        var pro = Profile()
        val database = FirebaseDatabase.getInstance()
        val Refpro = database.getReference("perfil")
        Refpro.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    pro = snapshot.getValue(Profile::class.java)!!
                    if (pro.id_user == auth.currentUser?.uid) {
                        usuarioext = true
                        Picasso.get().load(pro.imagen).into(root.profile_image);
                        /*val childUpdate = HashMap<String, Any>()
                        childUpdate["visibilidad"] = false
                        Refhab.child(hab.id).updateChildren(childUpdate)*/
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                Log.w(
                    "Problemas",
                    "problema al encontrar el id del usuaerio",
                    p0.toException()
                )
            }
        })


        root.bt_cerrar_sesion.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            goToLoginActicity()
        }

        root.profile_image.setOnClickListener{
            var alertDialog = activity?.let{
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setMessage("Elige una opción")
                    setPositiveButton(
                        "Tomar fotografia")
                    { dialog, id ->
                        dispatchTakePicture()
                    }
                    setNegativeButton(
                        "Abir galeria"
                    )
                    { dialog, id ->
                        almacenamiento()
                    }
                }
                builder.create()
            }
            alertDialog?.show()
        }
        return root
    }

    private fun almacenamiento() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, 10)
    }

    private fun dispatchTakePicture(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also{
            startActivityForResult(takePictureIntent,11)
        }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK ){
            var path = data?.data
            if (requestCode == 10){
                profile_image.setImageURI(path)
                saveImage()
            }
            else if (requestCode == 11){
                var imageBitmap = data?.extras?.get("data") as Bitmap
                profile_image.setImageBitmap(imageBitmap)
                saveImage()
            }
        }
    }

    private fun saveImage() {
        val auth = FirebaseAuth.getInstance()
        var storage = FirebaseStorage.getInstance()
        val photoRef = storage.reference.child("User").child(auth.currentUser?.uid.toString())

        profile_image?.isDrawingCacheEnabled = true
        profile_image?.buildDrawingCache()
        var bitmap = (profile_image?.drawable as BitmapDrawable).bitmap
        var baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        var data = baos.toByteArray()

        var uploadTask = photoRef.putBytes(data)
        var urlTask: Task<Uri> =
            uploadTask.continueWithTask(Continuation<   UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation photoRef.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var downloadUri = task.result
                    saveUser(downloadUri.toString())
                    Toast.makeText(activity!!.applicationContext, "Imagen de Perfil Actualizada", Toast.LENGTH_SHORT).show()
                } else {
                    //comentario
                    Toast.makeText(activity!!.applicationContext, "No se pudo actualizar la imagen de perfil", Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun saveUser(url :String){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("perfil")
        if(!usuarioext){
            var idProf = myRef.push().key
            val auth = FirebaseAuth.getInstance()
            var prof = com.ricardotangarife.traveltosanto.model.Profile(
                idProf!!,
                auth.currentUser?.uid.toString(),
                url
            )
            myRef.child(idProf).setValue(prof)
        }
        else if(usuarioext){
            var pro = Profile()
            val auth = FirebaseAuth.getInstance()
            val Refpro = database.getReference("perfil")
            Refpro.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        pro = snapshot.getValue(Profile::class.java)!!
                        if (pro.id_user == auth.currentUser?.uid) {
                            val childUpdate = HashMap<String, Any>()
                            childUpdate["imagen"] = url
                            Refpro.child(pro.id).updateChildren(childUpdate)
                        }
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.w(
                        "Problemas",
                        "problema al encontrar el id del usuaerio",
                        p0.toException()
                    )
                }
            })
        }
    }

    private fun goToLoginActicity(){
        var intentlogin = Intent(context, LoginActivity::class.java)
        intentlogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intentlogin)
    }

}
