package com.example.samsonaiyegbusi.events2you.GettersAndSetters;

import java.util.Date;

/**
 * Created by samsonaiyegbusi on 30/03/16.
 */
public class EventsFactory {

    private String EventID;

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String eventID) {
        EventID = eventID;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public String getEventStartTime() {
        return EventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        EventStartTime = eventStartTime;
    }

    public String getEventFinishTime() {
        return EventFinishTime;
    }

    public void setEventFinishTime(String eventFinishTime) {
        EventFinishTime = eventFinishTime;
    }

    public String getEventAddress() {
        return EventAddress;
    }

    public void setEventAddress(String eventAddress) {
        EventAddress = eventAddress;
    }

    public byte[] getEventImage() {
        return EventImage;
    }

    public void setEventImage(byte[] eventImage) {
        EventImage = eventImage;
    }

    public String getEventGenre() {
        return EventGenre;
    }

    public void setEventGenre(String eventGenre) {
        EventGenre = eventGenre;
    }

    public String getEventUsername() {
        return EventUsername;
    }

    public void setEventUsername(String eventUsername) {
        EventUsername = eventUsername;
    }

    public String getEventDescription() {
        return EventDescription;
    }

    public void setEventDescription(String eventDescription) {
        EventDescription = eventDescription;
    }

    private String EventName;
    private String EventDate;
    private String EventStartTime;
    private String EventFinishTime;
    private String EventAddress;
    private byte[] EventImage;
    private String EventGenre;
    private String EventUsername;
    private String EventDescription;
}
