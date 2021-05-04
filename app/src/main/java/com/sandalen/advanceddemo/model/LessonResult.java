package com.sandalen.advanceddemo.model;

import java.util.List;

public class LessonResult {
    private int status;
    private String message;
    private List<Lesson> datas;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Lesson> getDatas() {
        return datas;
    }

    public void setDatas(List<Lesson> datas) {
        this.datas = datas;
    }
}
