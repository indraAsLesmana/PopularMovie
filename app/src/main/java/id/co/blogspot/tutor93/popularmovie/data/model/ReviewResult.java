package id.co.blogspot.tutor93.popularmovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by indraaguslesmana on 6/30/17.
 */

public class ReviewResult implements Parcelable {

    public String id;

    public String author;

    public String content;

    public String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }

    public ReviewResult() {
    }

    protected ReviewResult(Parcel in) {
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<ReviewResult> CREATOR = new Parcelable.Creator<ReviewResult>() {
        @Override
        public ReviewResult createFromParcel(Parcel source) {
            return new ReviewResult(source);
        }

        @Override
        public ReviewResult[] newArray(int size) {
            return new ReviewResult[size];
        }
    };
}
