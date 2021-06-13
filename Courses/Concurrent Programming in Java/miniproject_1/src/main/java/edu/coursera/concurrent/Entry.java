package edu.coursera.concurrent;


public final class Entry {

    public final Integer object;

    public Entry next;

    Entry(final Integer setObject) {
        this.object = setObject;
    }
}
