package com.arny.pik.data.models

import com.arny.pik.utils.Utility
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Flat {
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("status")
    @Expose
    var status: Status? = null
    @SerializedName("roomQuantity")
    @Expose
    var roomQuantity: String? = null
    @SerializedName("stageNumber1")
    @Expose
    var stageNumber1: Int = 0
    @SerializedName("wholeAreaBti")
    @Expose
    var wholeAreaBti: String? = null
    @SerializedName("wholePrice")
    @Expose
    var wholePrice: String? = null
    @SerializedName("reserve_cost")
    @Expose
    var reserveCost: String? = null
    @SerializedName("end_cost")
    @Expose
    var endCost: String? = null
    @SerializedName("officePrice")
    @Expose
    var officePrice: String? = null
    @SerializedName("planId")
    @Expose
    var planId: String? = null
    @SerializedName("planing")
    @Expose
    var planing: Planing? = null
    @SerializedName("finishing")
    @Expose
    var finishing: Boolean? = null
    @SerializedName("discount")
    @Expose
    var discount: String? = null
    @SerializedName("angle")
    @Expose
    var angle: String? = null

    override fun toString(): String {
        return Utility.getFields(this)
    }
}
