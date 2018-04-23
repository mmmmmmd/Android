// IMyAidlInterface.aidl
package com.example.eastsun.ipc;

// Declare any non-default types here with import statements
import com.example.eastsun.ipc.bean.Person;

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    void addPerson(in Person person);
    List<Person> getPersonList();
}
