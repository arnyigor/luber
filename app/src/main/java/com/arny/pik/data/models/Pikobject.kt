package com.arny.pik.data.models

import com.arny.pik.utils.Utility
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Pikobject : Serializable {
    @SerializedName("id")
    @Expose
    var id: Long = 0
    @SerializedName("guid")
    @Expose
    var guid: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("address")
    @Expose
    var address: String? = null
    @SerializedName("latitude")
    @Expose
    var latitude: String? = null
    @SerializedName("longitude")
    @Expose
    var longitude: String? = null
    @SerializedName("hidePrice")
    @Expose
    var hidePrice: String? = null
    @SerializedName("last")
    @Expose
    var last: Int? = null
    @SerializedName("sticker")
    @Expose
    var sticker: Sticker? = null
    @SerializedName("metro")
    @Expose
    var metro: String? = null
    @SerializedName("time_on_transport")
    @Expose
    var timeOnTransport: String? = null
    @SerializedName("time_on_foot")
    @Expose
    var timeOnFoot: String? = null
    @SerializedName("minPrice")
    @Expose
    var minPrice: Int? = null
    @SerializedName("currency")
    @Expose
    var currency: String? = null
    @SerializedName("maxDiscount")
    @Expose
    var maxDiscount: Int? = null
    @SerializedName("nearSettlementDate")
    @Expose
    var nearSettlementDate: Int? = null
    @SerializedName("flats_free")
    @Expose
    var flatsFree: Int? = null
    @SerializedName("flats_reserved")
    @Expose
    var flatsReserved: Int? = null
    @SerializedName("flats_all")
    @Expose
    var flatsAll: Int? = null
    @SerializedName("img")
    @Expose
    var img: Img? = null
    @SerializedName("location")
    @Expose
    var location: String? = null
    @SerializedName("locations")
    @Expose
    var locations: Locations? = null
    @SerializedName("sort")
    @Expose
    var sort: Int? = null
    @SerializedName("new_design")
    @Expose
    var newDesign: NewDesign? = null

    override fun toString(): String {
        return Utility.getFields(this)
    }
}
