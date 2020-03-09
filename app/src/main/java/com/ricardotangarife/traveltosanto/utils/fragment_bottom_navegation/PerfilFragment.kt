package com.ricardotangarife.traveltosanto.utils.fragment_bottom_navegation


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.ricardotangarife.traveltosanto.LoginActivity

import com.ricardotangarife.traveltosanto.R
import kotlinx.android.synthetic.main.fragment_perfil.*
import kotlinx.android.synthetic.main.fragment_perfil.view.*

/**
 * A simple [Fragment] subclass.
 */
class PerfilFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_perfil, container, false)

        root.bt_cerrar_sesion.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            goToLoginActicity()
        }

        return root
    }

    private fun goToLoginActicity(){
        var intentlogin = Intent(context, LoginActivity::class.java)
        intentlogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intentlogin)
    }

}
