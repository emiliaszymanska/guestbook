package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Guestbook {

    private List<Entry> entries;

    public Guestbook() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(Entry entry) {
        this.entries.add(entry);
    }

    public void removeEntry(int index) {
        this.entries.remove(index);
    }

    public Entry getEntry(int index) {
        return this.entries.get(index);
    }

    public List<Entry> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        String entriesToString = "";
        int i = 1;
        for (Entry entry : entries) {
            entriesToString += i++ + ". " + entry;
        }
        return entriesToString;
    }
}
