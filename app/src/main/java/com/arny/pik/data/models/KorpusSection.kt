package com.arny.pik.data.models

import com.arny.pik.utils.Utility
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class KorpusSection {
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("sectionName")
    @Expose
    var sectionName: String? = null
    @SerializedName("quantity")
    @Expose
    var quantity: String? = null
    @SerializedName("maxFlatsOnFloor")
    @Expose
    var maxFlatsOnFloor: Int = 0
    @SerializedName("firstFloorNumber")
    @Expose
    var firstFloorNumber: String? = null
    @SerializedName("readiness")
    @Expose
    var readiness: String? = null
    @SerializedName("flatsDirection")
    @Expose
    var flatsDirection: String? = null
    @SerializedName("plan")
    @Expose
    var plan: String? = null
    @SerializedName("floors")
    @Expose
    var floors: LinkedHashMap<Int, Floor>? = null

    override fun toString(): String {
        return Utility.getFields(this)
    }
}
