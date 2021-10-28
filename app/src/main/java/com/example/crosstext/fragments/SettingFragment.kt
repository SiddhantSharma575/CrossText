package com.example.crosstext.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.crosstext.R
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import de.hdodenhof.circleimageview.CircleImageView

class SettingFragment : Fragment() {

    private lateinit var auth:FirebaseAuth
    private var user:FirebaseUser? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser

        val circleImageView = view.findViewById<CircleImageView>(R.id.userNameImg)
        val textViewTV = view.findViewById<TextView>(R.id.userNameFG)
        val utilTv = view.findViewById<TextView>(R.id.utilTV)
        val switchST:SwitchMaterial = view.findViewById(R.id.switchMat)

        textViewTV.text = user?.displayName
        Glide.with(requireActivity()).load(user?.photoUrl).into(circleImageView)
    }

}