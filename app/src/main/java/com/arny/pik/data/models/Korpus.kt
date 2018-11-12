package com.arny.pik.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Korpus {
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("color")
    @Expose
    var color: String? = null
    @SerializedName("coords")
    @Expose
    var coords: String? = null
    @SerializedName("coordsOuter")
    @Expose
    var coordsOuter: String? = null
    @SerializedName("dateOfMovingIn")
    @Expose
    var dateOfMovingIn: String? = null
    @SerializedName("minprice_s")
    @Expose
    var minpriceS: String? = null
    @SerializedName("minprice_1")
    @Expose
    var minprice1: String? = null
    @SerializedName("minprice_2")
    @Expose
    var minprice2: String? = null
    @SerializedName("minprice_3")
    @Expose
    var minprice3: String? = null
    @SerializedName("minprice_4")
    @Expose
    var minprice4: String? = null
    @SerializedName("minprice_5")
    @Expose
    var minprice5: String? = null
    @SerializedName("free")
    @Expose
    var free: String? = null
    @SerializedName("busy")
    @Expose
    var busy: String? = null
    @SerializedName("sold")
    @Expose
    var sold: String? = null
    @SerializedName("discount")
    @Expose
    var discount: String? = null
    @SerializedName("is_online")
    @Expose
    var isOnline: Boolean? = null
    @SerializedName("finishing")
    @Expose
    var finishing: String? = null

    override fun toString(): String {
        return title?:""
    }

}
