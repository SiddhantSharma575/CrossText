package com.example.crosstext.db

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class CopyRepository(application: Application) {
    private var copyDatabase:CopyDatabase
    private var getAllData:LiveData<List<CopyData>>

    init {
        copyDatabase = CopyDatabase.getDatabase(application)
        getAllData  = copyDatabase.getCopyDao().getAllData()
    }

    fun insertAll(dataList:List<CopyData>){
        CoroutineScope(IO).launch {
            copyDatabase.getCopyDao().insertAll(dataList)
        }
    }
    fun getAllData():LiveData<List<CopyData>>{
        return getAllData
    }

}