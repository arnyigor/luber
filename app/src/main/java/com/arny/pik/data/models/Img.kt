package com.arny.pik.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Img {
    @SerializedName("main")
    @Expose
    var main: String? = null
}
