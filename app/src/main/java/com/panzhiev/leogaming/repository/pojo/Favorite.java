package com.panzhiev.leogaming.repository.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favorite implements Parcelable{

    @SerializedName("service")
    @Expose
    private Integer service;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("account1")
    @Expose
    private String account1;

    protected Favorite(Parcel in) {
        if (in.readByte() == 0) {
            service = null;
        } else {
            service = in.readInt();
        }
        name = in.readString();
        account1 = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount1() {
        return account1;
    }

    public void setAccount1(String account1) {
        this.account1 = account1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (service == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(service);
        }
        dest.writeString(name);
        dest.writeString(account1);
    }
}
