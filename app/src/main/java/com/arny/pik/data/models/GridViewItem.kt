package com.arny.pik.data.models

import com.arny.pik.data.Local
import com.arny.pik.utils.Utility

class GridViewItem {
    var flat: Flat? = null
    var floorNum: Int = 0
    var type: Local.GridItemType? = null

    override fun toString(): String {
        return Utility.getFields(this)
    }
}
