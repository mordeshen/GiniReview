package com.e.tbreview.api.response

import android.os.Parcelable
import com.e.tbreview.model.ItemModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class ItemResponse(
    @SerializedName("name")
    @Expose
    var name : String = "",

    @SerializedName("thumbnail")
    @Expose
    var thumbnail : String = "",

    @SerializedName("description")
    @Expose
    var description : String = ""
) {
    override fun toString(): String {
        return "ItemResponse(name='$name', thumbnail='$thumbnail', description='$description')"
    }
}