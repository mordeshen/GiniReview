package com.e.tbreview.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.stream.Stream


@Entity(tableName = "tb_color")
data class ModelContentProvider(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pk")
    var pk: Int,

    @ColumnInfo(name = "color")
    var color: String

) {
    override fun toString(): String {
        return "ModelContent(pk=$pk, color='$color')"
    }
}