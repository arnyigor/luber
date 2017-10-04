package com.arny.lubereckiy.models;

import com.arny.arnylib.utils.Utility;
import com.arny.lubereckiy.common.Local;
public class GridViewItem {
    private Flat flat;
    private int floorNum;
    private Local.GridItemType type;

    public Flat getFlat() {
        return flat;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    public Local.GridItemType getType() {
        return type;
    }

    public void setType(Local.GridItemType type) {
        this.type = type;
    }

    public int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    @Override
    public String toString() {
        return Utility.getColumns(this);
    }
}
