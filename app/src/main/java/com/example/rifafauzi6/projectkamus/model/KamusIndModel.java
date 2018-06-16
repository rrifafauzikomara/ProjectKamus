package com.example.rifafauzi6.projectkamus.model;

import android.os.Parcel;
import android.os.Parcelable;

public class KamusIndModel implements Parcelable {

    private int id;
    private String word, description;

    public KamusIndModel(String word, String description) {
        this.word = word;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.word);
        dest.writeString(this.description);
    }

    public KamusIndModel() {
    }

    private KamusIndModel(Parcel in) {
        this.id = in.readInt();
        this.word = in.readString();
        this.description = in.readString();
    }

    public static final Creator<KamusIndModel> CREATOR = new Creator<KamusIndModel>() {
        @Override
        public KamusIndModel createFromParcel(Parcel source) {
            return new KamusIndModel(source);
        }

        @Override
        public KamusIndModel[] newArray(int size) {
            return new KamusIndModel[size];
        }
    };
}
