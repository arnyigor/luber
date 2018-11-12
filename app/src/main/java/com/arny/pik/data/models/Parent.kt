package com.arny.pik.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Parent {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

}
