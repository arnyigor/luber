package com.arny.pik.data.models

import com.arny.pik.utils.Utility
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GenPlan {
    @Expose
    var title: String? = null
    @SerializedName("data")
    @Expose
    var data: Data? = null
    override fun toString(): String {
        return Utility.getFields(this)
    }
}
