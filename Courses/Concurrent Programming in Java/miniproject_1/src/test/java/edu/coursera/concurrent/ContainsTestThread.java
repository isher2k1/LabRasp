package edu.coursera.concurrent;

import java.util.Random;

public class ContainsTestThread extends TestThread implements Runnable {
    private int nSuccessful = 0;
    private int nFailed = 0;

    public ContainsTestThread(final SequenceGenerator seq, final int seqToUse, final ListSet setL) {
        super(seq, seqToUse, setL);
    }

    @Override
    public void run() {
        for (int i = 0; i < nums.length; i++) {
            if (l.contains(nums[i])) {
                nSuccessful++;
            } else {
                nFailed++;
            }
        }
    }

    public int getNSuccessful() {
        return nSuccessful;
    }

    public int getNFailed() {
        return nFailed;
    }
}
