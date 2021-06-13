package edu.coursera.concurrent;

import java.util.Random;

public abstract class TestThread {

    protected ListSet l;

  
    protected final Integer[] nums;

    public TestThread(final SequenceGenerator seq, final int seqToUse,
            final ListSet setL) {
        this.nums = new Integer[seqToUse];
        for (int i = 0; i < this.nums.length; i++) {
            this.nums[i] = new Integer(seq.next());
        }
        this.l = setL;
    }
}
