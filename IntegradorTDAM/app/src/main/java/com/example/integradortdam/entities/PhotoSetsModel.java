package com.example.integradortdam.entities;

import java.util.ArrayList;

public class PhotoSetsModel {
    private int cancreate;
    private int page;
    private int pages;
    private int perpage;
    private int total;
    private ArrayList photoset;
    private String stat;

    public int getCancreate() { return cancreate; }
    public void setCancreate(int cancreate) { this.cancreate = cancreate; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }

    public int getPerpage() { return perpage; }
    public void setPerpage(int perpage) { this.perpage = perpage; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public ArrayList getPhotoset() { return photoset; }
    public void setPhotoset(ArrayList photoset) { this.photoset = photoset; }

    public String getStat() { return stat; }
    public void setStat(String stat) { this.stat = stat; }
}
