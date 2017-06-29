package id.co.blogspot.tutor93.popularmovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by indraaguslesmana on 6/30/17.
 */

public class Videos implements Parcelable {

    public Integer id;

    public List<VideoResult> results = null;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeList(this.results);
    }

    public Videos() {
    }

    protected Videos(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = new ArrayList<VideoResult>();
        in.readList(this.results, VideoResult.class.getClassLoader());
    }

    public static final Parcelable.Creator<Videos> CREATOR = new Parcelable.Creator<Videos>() {
        @Override
        public Videos createFromParcel(Parcel source) {
            return new Videos(source);
        }

        @Override
        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };
}
