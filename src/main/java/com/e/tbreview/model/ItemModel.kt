package com.e.tbreview.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "item_model")
data class ItemModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pk")
    var pk:Int = -1,

    @ColumnInfo(name = "cellKind")
    var cellKind: Int = 1,

    @ColumnInfo(name = "color")
    var color: String ="",

    @SerializedName("name")
    @Expose
    @ColumnInfo
    var name : String = "",

    @SerializedName("thumbnail")
    @Expose
    @ColumnInfo
    var thumbnail : String = "",

    @SerializedName("description")
    @Expose
    @ColumnInfo
    var description : String = ""
){
    override fun toString(): String {
        return "ItemModel(pk=$pk, cellKind=$cellKind, name='$name', thumbnail='$thumbnail', description='$description')"
    }

}