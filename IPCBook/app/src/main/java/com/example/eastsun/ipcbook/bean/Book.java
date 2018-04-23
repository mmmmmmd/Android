package com.example.eastsun.ipcbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eastsun on 18-4-11.
 */

public class Book implements Parcelable {

    private String bookName;
    private int bookId;

    public Book(String bookName,int bookId){
        this.bookName=bookName;
        this.bookId=bookId;
    }

    protected Book(Parcel in) {
        bookName=in.readString();
        bookId=in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookName);
        dest.writeInt(bookId);
    }
}
