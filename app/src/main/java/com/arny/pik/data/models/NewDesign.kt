package com.arny.pik.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewDesign {
    @SerializedName("imageMap")
    @Expose
    var imageMap: String? = null

}
