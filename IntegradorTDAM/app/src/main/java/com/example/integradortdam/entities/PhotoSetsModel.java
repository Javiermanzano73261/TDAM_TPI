package com.example.integradortdam.entities;

import java.util.List;

public class PhotoSetsModel {

    private int page;
    private int pages;
    private int perpage;
    private int total;
    private List<AlbumModel> photoset;


    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }

    public int getPerpage() { return perpage; }
    public void setPerpage(int perpage) { this.perpage = perpage; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public List<AlbumModel> getPhotoset() { return photoset; }
    public void setPhotoset(List<AlbumModel> photoset) { this.photoset = photoset; }

}
