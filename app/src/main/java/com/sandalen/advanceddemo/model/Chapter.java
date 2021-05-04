package com.sandalen.advanceddemo.model;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
    public static final String TABLE_NAME = "tb_chapter";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";

    private int id;
    private String name;

    private List<ChapterItem> children = new ArrayList<>();

    public Chapter(){}

    public Chapter(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addChild(int id,String childName){
        ChapterItem chapterItem = new ChapterItem(id, childName);
        chapterItem.setId(getId());
        children.add(chapterItem);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChapterItem> getChildren() {
        return children;
    }

    public void setChildren(List<ChapterItem> children) {
        this.children = children;
    }
}
