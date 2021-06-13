package edu.coursera.concurrent;

import edu.rice.pcdp.Actor;
import static edu.rice.pcdp.PCDP.finish;



public final class SieveActor extends Sieve {

    @Override
    public int countPrimes(final int limit) 
    {
    	final SieveActorActor sieveActor = new SieveActorActor();
        finish(() -> {
            for (int i = 3; i <= limit; i += 2) {
                sieveActor.send(i);
            }
            sieveActor.send(0);
        });


        int totalPrimes = 1;
        SieveActorActor currentActor = sieveActor;
        while (currentActor != null) {
            totalPrimes += currentActor.numLocalPrimes;
            currentActor = currentActor.nextActor;
        }

        return totalPrimes;

    }

    public static final class SieveActorActor extends Actor {

    	
    	static final int MAX_LOCAL_PRIMES = 1000;

        SieveActorActor nextActor = null;
        
        int[] localPrimes = new int[MAX_LOCAL_PRIMES];
        int numLocalPrimes = 0;
        
        @Override
        public void process(final Object msg) {
            int candidate = (int) msg;


            if (candidate <= 0) {
                if (nextActor != null) {
                    nextActor.send(msg);
                }
                return;
            }


            if (!isLocalPrime(candidate)) {
                return;
            }


            if (numLocalPrimes < MAX_LOCAL_PRIMES) {
                localPrimes[numLocalPrimes++] = candidate;
                return;
            }


            if (nextActor == null) {
                nextActor = new SieveActorActor();
            }

            nextActor.send(msg);
        }

        boolean isLocalPrime(int candidate) {
            for (int i = 0; i < numLocalPrimes; i++) {

                if (candidate % localPrimes[i] == 0) {
                    return false;
                }
            }

            return true;
        }
    }    
}
