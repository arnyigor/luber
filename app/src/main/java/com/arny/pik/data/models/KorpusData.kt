package com.arny.pik.data.models

import com.arny.pik.utils.Utility
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class KorpusData {
    @SerializedName("sections")
    @Expose
    var sections: List<KorpusSection>? = null

    override fun toString(): String {
        return Utility.getFields(this)
    }
}
