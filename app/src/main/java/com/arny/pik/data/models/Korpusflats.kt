package com.arny.pik.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Korpusflats {
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("titleOfCorps")
    @Expose
    var titleOfCorps: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("korpusSections")
    @Expose
    var korpusSections: List<KorpusSection>? = null

}
