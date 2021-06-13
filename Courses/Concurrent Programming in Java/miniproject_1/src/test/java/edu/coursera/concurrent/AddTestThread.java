package edu.coursera.concurrent;

import java.util.Random;

public class AddTestThread extends TestThread implements Runnable {
    public AddTestThread(final SequenceGenerator seq, final int seqToUse, final ListSet setL) {
        super(seq, seqToUse, setL);
    }

    @Override
    public void run() {
        for (int i = 0; i < nums.length; i++) {
            l.add(nums[i]);
        }
    }
}
