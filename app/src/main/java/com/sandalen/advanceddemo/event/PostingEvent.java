package com.sandalen.advanceddemo.event;

public class PostingEvent {
    private String threadInfo;

    public PostingEvent(String threadInfo) {
        this.threadInfo = threadInfo;
    }

    public String getThreadInfo() {
        return threadInfo;
    }

    public void setThreadInfo(String threadInfo) {
        this.threadInfo = threadInfo;
    }
}
