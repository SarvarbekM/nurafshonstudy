package com.example.nurafshonstudy.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Answer implements Parcelable {
    @SerializedName("text")
    private String text;
    @SerializedName("isCorrect")
    private boolean isCorrect;
    @SerializedName("isSelected")
    private boolean isSelected;

    public Answer() {
    }

    public Answer(String text, boolean isCorrect, boolean isSelected) {
        this.text = text;
        this.isCorrect = isCorrect;
        this.isSelected = isSelected;
    }

    public Answer(Parcel parcel){
        this.text=parcel.readString();
        this.isCorrect=parcel.readByte() != 0;
        this.isSelected=parcel.readByte() != 0;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "text='" + text + '\'' +
                ", isCorrect=" + isCorrect +
                ", isSelected=" + isSelected +
                '}';
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeByte((byte) (this.isCorrect ? 1 : 0));
        dest.writeByte((byte) (this.isSelected ? 1 : 0));
    }

    private static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

    public static Creator<Answer> getCREATOR() {
        return CREATOR;
    }

    public boolean equal(Answer other){
        return this.text.equals(other.getText()) && this.isCorrect==other.isCorrect;
    }
}
