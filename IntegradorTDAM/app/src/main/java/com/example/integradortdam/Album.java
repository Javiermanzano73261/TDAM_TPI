package com.example.integradortdam;

import java.util.ArrayList;


public class Album {

        private long id;
        private String primary;
        private String owner;
        private String ownername;
        private ArrayList photo;
        private int page;
        private int per_page;
        private int pages;
        private String title;
        private int total;
        private String stat;

        public long getId() { return id; }
        public void setId(long id) { this.id = id; }

        public String getPrimary() { return primary; }
        public void setPrimary(String primary) { this.primary = primary; }

        public String getOwner() { return owner; }
        public void setOwner(String owner) { this.owner = owner; }

        public String getOwnername() { return ownername; }
        public void setOwnername(String ownername) { this.ownername = ownername; }

        public ArrayList getPhoto() { return photo; }
        public void setPhoto(ArrayList photo) { this.photo = photo; }

        public int getPage() { return page; }
        public void setPage(int page) { this.page = page; }

        public int getPer_page() {return per_page; }
        public void setPer_page(int per_page) { this.per_page = per_page; }

        public int getPages() { return pages; }
        public void setPages(int pages) { this.pages = pages; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public int getTotal() { return total; }
        public void setTotal(int total) { this.total = total; }

        public String getStat() { return stat; }
        public void setStat(String stat) { this.stat = stat; }
}
