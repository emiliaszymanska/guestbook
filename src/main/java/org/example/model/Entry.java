package org.example.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.example.helpers.CustomLocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Entry {

    private String content;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime dateAndTime;
    private String authorName;

    public Entry() {
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Entry setContent(String content) {
        this.content = content;
        return this;
    }

    public Entry setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
        return this;
    }

    public Entry setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    @Override
    public String toString() {
        String entry = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateAndTime = this.dateAndTime.format(formatter);
        entry += this.content + " " + dateAndTime + " " + this.authorName + "\n";

        return entry;
    }
}
