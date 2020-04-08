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
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.StorageReference
//import com.google.firebase.storage.UploadTask
import com.ricardotangarife.traveltosanto.LoginActivity

import com.ricardotangarife.traveltosanto.R
import com.ricardotangarife.traveltosanto.SesionRoom
import com.ricardotangarife.traveltosanto.model.User
import com.ricardotangarife.traveltosanto.model.room.Reserva
import com.ricardotangarife.traveltosanto.model.room.ReservaDAO
import com.ricardotangarife.traveltosanto.model.room.ReservasRVAdapter
import kotlinx.android.synthetic.main.fragment_perfil.*
import kotlinx.android.synthetic.main.fragment_perfil.view.*
import java.io.ByteArrayOutputStream
import java.sql.Types.NULL

/**
 * A simple [Fragment] subclass.
 */
class PerfilFragment : Fragment() {

    var allReservas: List<Reserva>  = emptyList()
    val idUsuario = "primero"
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
        var allReservas: List<Reserva> = reservaDAO.getReservas()
        var reserRVAdapter = ReservasRVAdapter(
            activity!!.applicationContext,
            allReservas as ArrayList<Reserva>
        )
        rv_reservas.adapter = reserRVAdapter
        reserRVAdapter.notifyDataSetChanged()

        root.bt_cerrar_sesion.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            goToLoginActicity()
        }
        //val database = FirebaseDatabase.getInstance().reference


       /* root.profile_image.setOnClickListener{
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

        root.img_portada.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, 12)
        }*/*/
        return root
    }
/*
    private fun saveImage() {
        var storage = FirebaseStorage.getInstance()
        val photoRef = storage.reference.child("User").child(id.toString())

        profile_image?.isDrawingCacheEnabled = true
        profile_image?.buildDrawingCache()
        val bitmap = (profile_image?.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = photoRef.putBytes(data)
        val urlTask: Task<Uri> =
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation photoRef.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                 //   saveUser(downloadUri.toString())
                } else {
                    //comentario
                }
            }
    }*/


    private fun saveUser(urlFoto: String) {
        var user = User("juan", "id", "email", urlFoto)


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

   /* private fun leer_room() {
        val usuarioDAO = SesionRoom.database.UsuarioDAO()
        val usuario = usuarioDAO.searchUsuario("Juan")
        tv_nombre.text = usuario.nombre
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK ){
            val path = data?.data

            if (requestCode == 10){

                profile_image.setImageURI(path)
      /*          var uri = data?.getData()
                var filePath : StorageReference = mStorage.child("fotos").children(Uri?.lastPathSegment)
                filePath.putFile(uri).addOnSuccessListener {

                }
*/
            }
            else if (requestCode == 11){
                val imageBitmap = data?.extras?.get("data") as Bitmap
                profile_image.setImageBitmap(imageBitmap)
            }
            else if(requestCode == 12){
                img_portada.setImageURI(path)
            }

        }
    }
    private fun goToLoginActicity(){
        var intentlogin = Intent(context, LoginActivity::class.java)
        intentlogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intentlogin)
    }

}
