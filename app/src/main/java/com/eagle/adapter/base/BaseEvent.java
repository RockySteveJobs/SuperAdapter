package com.eagle.adapter.base;

import java.io.Serializable;

public class BaseEvent implements Serializable {
    private int eventId;
    private Object eventObj;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Object getEventObj() {
        return eventObj;
    }

    public void setEventObj(Object eventObj) {
        this.eventObj = eventObj;
    }

    public BaseEvent(int eventId) {
        this.eventId = eventId;
    }
}
