package com.jnu.booklistapp;

import java.io.Serializable;

public class Book implements Serializable {

    private long bookId;
    private int imageId;
    private String name;
    private String tag;

    public Book(int imageId,String name,String tag){
        this.bookId=System.currentTimeMillis();;
        this.imageId=imageId;
        this.name=name;
        this.tag=tag;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }
}
