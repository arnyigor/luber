
package com.arny.lubereckiy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pikobject {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("hidePrice")
    @Expose
    private String hidePrice;
    @SerializedName("last")
    @Expose
    private Integer last;
    @SerializedName("sticker")
    @Expose
    private Sticker sticker;
    @SerializedName("metro")
    @Expose
    private String metro;
    @SerializedName("time_on_transport")
    @Expose
    private String timeOnTransport;
    @SerializedName("time_on_foot")
    @Expose
    private String timeOnFoot;
    @SerializedName("minPrice")
    @Expose
    private Integer minPrice;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("maxDiscount")
    @Expose
    private Integer maxDiscount;
    @SerializedName("nearSettlementDate")
    @Expose
    private Integer nearSettlementDate;
    @SerializedName("flats_free")
    @Expose
    private Integer flatsFree;
    @SerializedName("flats_reserved")
    @Expose
    private Integer flatsReserved;
    @SerializedName("flats_all")
    @Expose
    private Integer flatsAll;
    @SerializedName("img")
    @Expose
    private Img img;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("locations")
    @Expose
    private Locations locations;
    @SerializedName("sort")
    @Expose
    private Integer sort;
    @SerializedName("new_design")
    @Expose
    private NewDesign newDesign;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getHidePrice() {
        return hidePrice;
    }

    public void setHidePrice(String hidePrice) {
        this.hidePrice = hidePrice;
    }

    public Integer getLast() {
        return last;
    }

    public void setLast(Integer last) {
        this.last = last;
    }

    public Sticker getSticker() {
        return sticker;
    }

    public void setSticker(Sticker sticker) {
        this.sticker = sticker;
    }

    public String getMetro() {
        return metro;
    }

    public void setMetro(String metro) {
        this.metro = metro;
    }

    public String getTimeOnTransport() {
        return timeOnTransport;
    }

    public void setTimeOnTransport(String timeOnTransport) {
        this.timeOnTransport = timeOnTransport;
    }

    public String getTimeOnFoot() {
        return timeOnFoot;
    }

    public void setTimeOnFoot(String timeOnFoot) {
        this.timeOnFoot = timeOnFoot;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Integer maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public Integer getNearSettlementDate() {
        return nearSettlementDate;
    }

    public void setNearSettlementDate(Integer nearSettlementDate) {
        this.nearSettlementDate = nearSettlementDate;
    }

    public Integer getFlatsFree() {
        return flatsFree;
    }

    public void setFlatsFree(Integer flatsFree) {
        this.flatsFree = flatsFree;
    }

    public Integer getFlatsReserved() {
        return flatsReserved;
    }

    public void setFlatsReserved(Integer flatsReserved) {
        this.flatsReserved = flatsReserved;
    }

    public Integer getFlatsAll() {
        return flatsAll;
    }

    public void setFlatsAll(Integer flatsAll) {
        this.flatsAll = flatsAll;
    }

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Locations getLocations() {
        return locations;
    }

    public void setLocations(Locations locations) {
        this.locations = locations;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public NewDesign getNewDesign() {
        return newDesign;
    }

    public void setNewDesign(NewDesign newDesign) {
        this.newDesign = newDesign;
    }

    @Override
    public String toString() {
        return "title:" + name;
    }
}
