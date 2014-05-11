package br.com.unirio.moodle.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabiana on 11/05/2014.
 */
public class CoursesParcel implements Parcelable {
    private List<Course> rows;

    public CoursesParcel() {
    }

    public CoursesParcel(List<Course> rows) {
        this.rows = rows;
    }

    public List<Course> getRows() {
        return rows;
    }

    public void setRows(List<Course> rows) {
        this.rows = rows;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(rows);
    }

    public static final Creator<CoursesParcel> CREATOR = new Creator<CoursesParcel>() {
        @Override
        public CoursesParcel createFromParcel(Parcel parcel) {
            CoursesParcel courseParcel = new CoursesParcel();
            List<Course> infoRows = new ArrayList<Course>();
            parcel.readList(infoRows, null);
            courseParcel.rows = infoRows;
            return courseParcel;
        }

        @Override
        public CoursesParcel[] newArray(int i) {
            return new CoursesParcel[i];
        }
    };
}
