package com.example.crosstext.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crosstext.R
import com.example.crosstext.adapters.DataViewAdapter
import com.example.crosstext.db.CopyData
import com.example.crosstext.db.CopyRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class DataFragment : Fragment() {
    private lateinit var dataViewModel: DataViewModel
    private lateinit var dataList:ArrayList<CopyData>
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataViewAdapter: DataViewAdapter
    private lateinit var floatBtn:FloatingActionButton
    private lateinit var builder:AlertDialog.Builder
    private lateinit var dialog: AlertDialog
    private lateinit var db:FirebaseFirestore
    private lateinit var titleEt:TextInputEditText
    private lateinit var textEt:TextInputEditText
    private lateinit var cancelBtn:Button
    private lateinit var addBtn:Button
    private var get_Title:String = ""
    private var get_Text:String = ""
    private var get_BookMark:Boolean = false
    private lateinit var copyModel:CopyData
    private lateinit var copyRepository: CopyRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_data_fragement, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataViewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        db = FirebaseFirestore.getInstance()
        floatBtn = view.findViewById(R.id.floatActionBtn)
        recyclerView = view.findViewById(R.id.mainRecView)
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        dataList  = ArrayList()
        dataViewAdapter = DataViewAdapter(requireContext(),dataList.reversed())
        copyRepository = CopyRepository(activity?.application!!)
        getDataFromDatabase()
        dataViewModel.getAllData().observe(viewLifecycleOwner, Observer {
            dataViewAdapter.setData(it.reversed())
            recyclerView.adapter = dataViewAdapter
        })
        floatBtn.setOnClickListener {
            openDialog()
        }
    }


    private fun getDataFromDatabase() {
        db.collection("CrossText")
            .addSnapshotListener { value, error ->
                dataList.clear()
              for (document in value!!){
                  dataList.add(
                      CopyData(document.getString("title").toString(),
                      document.getString("text").toString(),
                      document.getBoolean("bookMark") as Boolean)
                  )
              }
                dataViewModel.insert(dataList)
            }
    }

    private fun openDialog() {
        builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_layout,null)
        titleEt = view.findViewById(R.id.eTitle)
        textEt = view.findViewById(R.id.etText)
        addBtn = view.findViewById(R.id.btnAdd)
        cancelBtn = view.findViewById(R.id.btnCancel)
        dialog = builder.create()
        dialog.setView(view)
        dialog.show()
        addBtn.setOnClickListener {
            saveDataToFireStore()
        }
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun saveDataToFireStore() {
        get_Title = titleEt.text.toString().trim()
        get_Text = textEt.text.toString().trim()
        get_BookMark = false

        copyModel = CopyData(get_Title,get_Text,get_BookMark)
        db.collection("CrossText")
            .document()
            .set(copyModel)
            .addOnSuccessListener { 
                dialog.dismiss()
                Toast.makeText(activity, "Data Inserted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
    }
}