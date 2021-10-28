package com.example.crosstext.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crossText")
data class CopyData(
    val title:String,
    val text:String,
    val isBookMark:Boolean
    ) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}

