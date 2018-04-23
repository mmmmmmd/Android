package com.example.eastsun.ipcbook;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.eastsun.ipcbook.bean.Book;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by eastsun on 18-4-11.
 */

public class BookManagerService extends Service {

    private static final String TAG="BMS";
    private CopyOnWriteArrayList<Book> mBookList=new CopyOnWriteArrayList<Book>();

    private IBinder mBinder=new IMyBookInterface.Stub() {
        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
