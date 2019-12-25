package com.example.nurafshonstudy.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Test implements Parcelable {
    @SerializedName("question")
    private String question;
    @SerializedName("a")
    private Answer a;
    @SerializedName("b")
    private Answer b;
    @SerializedName("c")
    private Answer c;
    @SerializedName("d")
    private Answer d;

    public Test(String question, Answer a, Answer b, Answer c, Answer d) {
        this.question = question;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Test(Parcel parcel){
        this.question=parcel.readString();
        this.a=(Answer) parcel.readParcelable(Answer.class.getClassLoader());
        this.b=(Answer) parcel.readParcelable(Answer.class.getClassLoader());
        this.c=(Answer) parcel.readParcelable(Answer.class.getClassLoader());
        this.d=(Answer) parcel.readParcelable(Answer.class.getClassLoader());
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Answer getA() {
        return a;
    }

    public void setA(Answer a) {
        this.a = a;
    }

    public Answer getB() {
        return b;
    }

    public void setB(Answer b) {
        this.b = b;
    }

    public Answer getC() {
        return c;
    }

    public void setC(Answer c) {
        this.c = c;
    }

    public Answer getD() {
        return d;
    }

    public void setD(Answer d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "Test{" +
                "question='" + question + '\'' +
                ", a=" + a.toString() +
                ", b=" + b.toString() +
                ", c=" + c.toString() +
                ", d=" + d.toString() +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.question);
        dest.writeParcelable(this.a, flags);
        dest.writeParcelable(this.b, flags);
        dest.writeParcelable(this.c, flags);
        dest.writeParcelable(this.d, flags);
    }

    private static final Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel in) {
            return new Test(in);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };

    public static Creator<Test> getCREATOR() {
        return CREATOR;
    }

    public boolean equal(Test other){
        return this.question.equals(other.getQuestion()) &&
        this.a.equal(other.getA()) && this.b.equal(other.getB()) && this.c.equal(other.getC()) && this.d.equal(other.getD());
    }
}
