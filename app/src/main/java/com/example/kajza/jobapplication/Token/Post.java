
package com.example.kajza.jobapplication.Token;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {


    private String accessToken;

    public Post(String accessToken) {
        this.accessToken = accessToken;

    }

    protected Post(Parcel in) {
        accessToken = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accessToken);
    }
}

