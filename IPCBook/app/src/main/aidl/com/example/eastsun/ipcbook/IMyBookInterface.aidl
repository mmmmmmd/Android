// IMyBookInterface.aidl
package com.example.eastsun.ipcbook;

import com.example.eastsun.ipcbook.bean.Book;

interface IMyBookInterface {
    void addBook(in Book book);
    List<Book> getBookList();
}
