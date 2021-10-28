package com.example.crosstext.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.crosstext.db.CopyData
import com.example.crosstext.db.CopyRepository

class DataViewModel(application:Application) : AndroidViewModel(application) {
    private lateinit var repository: CopyRepository
    private lateinit var getAllList:LiveData<List<CopyData>>

    init {
        repository = CopyRepository(application)
        getAllList = repository.getAllData()
    }

    fun insert(dataList:List<CopyData>){
        repository.insertAll(dataList)
    }

    fun getAllData():LiveData<List<CopyData>>{
        return getAllList
    }
}