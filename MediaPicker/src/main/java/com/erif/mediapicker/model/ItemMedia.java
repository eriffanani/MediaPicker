package com.erif.mediapicker.model;

import android.net.Uri;

public class ItemMedia {
    int id = 0;
    String name;
    String path;
    Uri uri;
    long size = 0;
    int type = 0;
    boolean selected = false;

    public ItemMedia(int id, String name, String path, Uri uri, long size) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.uri = uri;
        this.size = size;
    }

    public ItemMedia(String name, String path, Uri uri, long size) {
        this.name = name;
        this.path = path;
        this.uri = uri;
        this.size = size;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getPath() {return path;}
    public void setPath(String path) {this.path = path;}

    public Uri getUri() {return uri;}
    public void setUri(Uri uri) {this.uri = uri;}

    public long getSize() {return size;}
    public void setSize(long size) {this.size = size;}

    public int getType() {return type;}
    public void setType(int type) {this.type = type;}

    public boolean isSelected() {return selected;}
    public void setSelected(boolean selected) {this.selected = selected;}
}
