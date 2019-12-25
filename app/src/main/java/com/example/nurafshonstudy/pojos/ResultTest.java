package com.example.nurafshonstudy.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultTest implements Parcelable {
    @SerializedName("subCategory")
    private String subCategory;
    @SerializedName("date")
    private Date date;
    @SerializedName("allTestCount")
    private int allTestCount;
    @SerializedName("correctTestCount")
    private int correctTestCount;

    public ResultTest(String subCategory, Date date, int allTestCount, int correctTestCount) {
        this.subCategory = subCategory;
        this.date = date;
        this.allTestCount = allTestCount;
        this.correctTestCount = correctTestCount;

    }

    public ResultTest(Parcel parcel){
        this.subCategory=parcel.readString();
        this.date=new Date(parcel.readLong());
        this.allTestCount=parcel.readInt();
        this.correctTestCount=parcel.readInt();
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAllTestCount() {
        return allTestCount;
    }

    public void setAllTestCount(int allTestCount) {
        this.allTestCount = allTestCount;
    }

    public int getCorrectTestCount() {
        return correctTestCount;
    }

    public void setCorrectTestCount(int correctTestCount) {
        this.correctTestCount = correctTestCount;
    }

    @Override
    public String toString() {
        return "ResultTest{" +
                "subCategory=" + subCategory +
                ", date=" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date )+
                ", allTestCount=" + allTestCount +
                ", correctTestCount=" + correctTestCount +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.subCategory);
        dest.writeLong(this.date.getTime());
        dest.writeInt(this.allTestCount);
        dest.writeInt(this.correctTestCount);
    }

    private static final Creator<ResultTest> CREATOR = new Creator<ResultTest>() {
        @Override
        public ResultTest createFromParcel(Parcel in) {
            return new ResultTest(in);
        }

        @Override
        public ResultTest[] newArray(int size) {
            return new ResultTest[size];
        }
    };

    public static Creator<ResultTest> getCREATOR() {
        return CREATOR;
    }
}
