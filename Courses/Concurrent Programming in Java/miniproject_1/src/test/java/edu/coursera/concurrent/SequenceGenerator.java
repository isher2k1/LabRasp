package edu.coursera.concurrent;


public interface SequenceGenerator {

    public int sequenceLength();

    public int next();

    public void reset();

    public String getLabel();
}
