package com.example.eastsun.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.eastsun.ipc.bean.Person;

import java.util.ArrayList;
import java.util.List;

public class MyAidlService extends Service {

    private String TAG = this.getClass().getSimpleName();

    private ArrayList<Person> mPersons;

    private IBinder mIBinder = new IMyAidlInterface.Stub() {

        @Override
        public void addPerson(Person person) throws RemoteException {
            mPersons.add(person);
        }

        @Override
        public List<Person> getPersonList() throws RemoteException {
            return mPersons;
        }
    };

    public MyAidlService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        mPersons = new ArrayList<>();
        Log.e(TAG,"MyAidlService onBind");
        return mIBinder;
    }
}
