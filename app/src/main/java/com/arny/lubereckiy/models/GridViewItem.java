package com.arny.lubereckiy.models;

import com.arny.lubereckiy.common.Local;
public class GridViewItem {
    private Flat flat;
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
}
