package edu.coursera.concurrent;

import java.util.List;
import java.util.ArrayList;


public final class SieveSequential extends Sieve {

    @Override
    public int countPrimes(final int limit) {
        final List<Integer> localPrimes = new ArrayList<Integer>();
        localPrimes.add(2);
        for (int i = 3; i <= limit; i += 2) {
            checkPrime(i, localPrimes);
        }

        return localPrimes.size();
    }

    private void checkPrime(final int candidate,
            final List<Integer> primesList) {
        boolean isPrime = true;
        final int s = primesList.size();
        for (int i = 0; i < s; ++i) {
            final Integer loopPrime = primesList.get(i);
            if (candidate % loopPrime.intValue() == 0) {
                isPrime = false;
                break;
            }
        }
        if (isPrime) {
            primesList.add(candidate);
        }
    }
}
