package com.arny.pik.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Locations {
    @SerializedName("parent")
    @Expose
    var parent: Parent? = null
    @SerializedName("child")
    @Expose
    var child: Child? = null

}
